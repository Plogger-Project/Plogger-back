package com.project.plogger.config;

import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.project.plogger.common.object.ChatMessage;
import com.project.plogger.common.object.InviteUser;
import com.project.plogger.common.object.JoinRoom;
import com.project.plogger.common.object.RoomInvite;
import com.project.plogger.entity.chat.ChatJoinEntity;
import com.project.plogger.provider.JwtProvider;
import com.project.plogger.repository.chat.ChatJoinRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SocketModule {
    
    private final ChatJoinRepository chatJoinRepository;

    private final SocketIOServer server;
    private final JwtProvider provider;

    public SocketModule(
        SocketIOServer server, 
        JwtProvider provider, 
        ChatJoinRepository chatJoinRepository
    ) {

        this.server = server;
        this.provider = provider;
        this.chatJoinRepository = chatJoinRepository;
        server.addConnectListener(OnConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", ChatMessage.class, onChatReceived());
        server.addEventListener("join_room", JoinRoom.class, onJoinRoom());
        server.addEventListener("invite_users", InviteUser.class, onInviteUser());
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
            RoomInvite roomInvite = new RoomInvite("system-invite", invitedPeople);
            server.getRoomOperations(roomId.toString()).sendEvent("receive_message", roomInvite);
        };
    }
}