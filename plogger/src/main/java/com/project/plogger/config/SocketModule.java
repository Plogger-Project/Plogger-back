package com.project.plogger.config;

import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.project.plogger.common.object.ChatMessage;
import com.project.plogger.common.object.InviteUser;
import com.project.plogger.common.object.JoinRoom;
import com.project.plogger.common.object.LeaveRoom;
import com.project.plogger.common.object.RoomInvite;
import com.project.plogger.entity.chat.ChatJoinEntity;
import com.project.plogger.entity.chat.ChatMessageEntity;
import com.project.plogger.provider.JwtProvider;
import com.project.plogger.repository.chat.ChatJoinRepository;
import com.project.plogger.repository.chat.ChatMessageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SocketModule {
    
    private final ChatJoinRepository chatJoinRepository;
    private final ChatMessageRepository chatMessageRepository;

    private final SocketIOServer server;
    private final JwtProvider provider;

    public SocketModule(
        SocketIOServer server, 
        JwtProvider provider, 
        ChatJoinRepository chatJoinRepository,
        ChatMessageRepository chatMessageRepository
    ) {

        this.server = server;
        this.provider = provider;
        this.chatJoinRepository = chatJoinRepository;
        this.chatMessageRepository = chatMessageRepository;
        server.addConnectListener(OnConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", ChatMessage.class, onChatReceived());
        server.addEventListener("join_room", JoinRoom.class, onJoinRoom());
        server.addEventListener("invite_users", InviteUser.class, onInviteUser());
        server.addEventListener("leave_room", LeaveRoom.class, onLeaveRoom());
    }

    private ConnectListener OnConnected() {
        return (client) -> {
            
            String token = client.getHandshakeData().getSingleUrlParam("accessToken");
            // String token = client.getHandshakeData().getHttpHeaders().get("accessToken");
    
            if (token != null) {
                log.info("Received token : {}", token);
    
                String userId = provider.validate(token);
    
                if (userId != null) {
                    log.info("User [{}] connected", userId);
                    client.set("userId", userId);
                } else {
                    log.error("Invalid JWT token for client [{}]", client.getSessionId().toString());
                    client.disconnect();
                } 
            } else {
                log.error("accessToken cookie missing for client [{}]", client.getSessionId().toString());
                client.disconnect(); 
            }
        };
    }

    private DisconnectListener onDisconnected() {
        return (client) -> {
            log.info("Client[{}] - Disconnected from socket", client.getSessionId().toString());
        };
    }

    private DataListener<ChatMessage> onChatReceived() {
        return (client, data, ack) -> {
            Integer roomId = data.getRoomId();
            String senderId = data.getSenderId();
            String message = data.getMessage();
            String sentAt = data.getSentAt();

            String roomIdStr = String.valueOf(roomId);

            ChatMessage chatMessage = new ChatMessage(roomId, senderId, message);
            ChatMessageEntity chatMessageEntity = new ChatMessageEntity(roomId, senderId, message);
            chatMessageRepository.save(chatMessageEntity);

            server.getRoomOperations(roomIdStr).sendEvent("receive_message", chatMessage);
            log.info("Received message for room: {}, sender: {}, message: {}, sentAt: {}", roomIdStr, senderId, message, sentAt);
        };
    }

    private DataListener<JoinRoom> onJoinRoom() {
        return (client, data, ack) -> {
            String userId = client.get("userId");
            Integer roomId = data.getRoomId();

            ChatJoinEntity chatJoinEntity = new ChatJoinEntity(roomId, userId);
            chatJoinRepository.save(chatJoinEntity);

            String message = userId + "님이 연결되었습니다.";

            client.joinRoom(roomId.toString());
            ChatMessage chatMessage = new ChatMessage(roomId, "system", message);
            server.getRoomOperations(roomId.toString()).sendEvent("receive_message", chatMessage);
        };
    }

    private DataListener<InviteUser> onInviteUser() {
        return (client, data, ack) -> {
            Integer roomId = data.getRoomId();
            String[] invitedPeople = data.getInvitedPeople();
    
            for (String invitedUserId : invitedPeople) {
                ChatJoinEntity chatJoinEntity = new ChatJoinEntity(roomId, invitedUserId);
                chatJoinRepository.save(chatJoinEntity);
                log.info("User [{}] invited to room [{}]", invitedUserId, roomId);
            }
            RoomInvite roomInvite = new RoomInvite("system-invite", invitedPeople, roomId);
            server.getRoomOperations(roomId.toString()).sendEvent("receive_message", roomInvite);
        };
    }

    private DataListener<LeaveRoom> onLeaveRoom() {
        return (client, data, ack) -> {
            Integer roomId = data.getRoomId();
            String userId = data.getUserId();

            chatJoinRepository.deleteByRoomIdAndUserId(roomId, userId);

            String message = userId + "님이 채팅방을 나갔습니다.";
            System.out.println(message);
            client.leaveRoom(roomId.toString());
            ChatMessage chatMessage = new ChatMessage(roomId, "system", message);
            server.getRoomOperations(roomId.toString()).sendEvent("receive_message", chatMessage);
            client.sendEvent("leave_anyone", chatMessage);
        };
    }
}