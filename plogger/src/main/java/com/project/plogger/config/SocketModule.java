package com.project.plogger.config;

import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.project.plogger.common.object.ChatMessage;
import com.project.plogger.common.object.JoinRoom;
import com.project.plogger.entity.chat.ChatJoinEntity;
import com.project.plogger.provider.JwtProvider;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.repository.chat.ChatJoinRepository;
import com.project.plogger.repository.chat.ChatMessageRepository;
import com.project.plogger.repository.chat.ChatRoomRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SocketModule {
    
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatJoinRepository chatJoinRepository;
    private final UserRepository userRepository;

    private final SocketIOServer server;
    private final JwtProvider provider;

    public SocketModule(
        SocketIOServer server, 
        JwtProvider provider, 
        ChatRoomRepository chatRoomRepository,
        ChatMessageRepository chatMessageRepository,
        ChatJoinRepository chatJoinRepository,
        UserRepository userRepository
    ) {

        this.server = server;
        this.provider = provider;
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.chatJoinRepository = chatJoinRepository;
        this.userRepository = userRepository;
        server.addConnectListener(OnConnected());
        server.addDisconnectListener(onDisconnected());
        server.addEventListener("send_message", ChatMessage.class, onChatReceived());
        server.addEventListener("join_room", JoinRoom.class, onJoinRoom());
    }

    private ConnectListener OnConnected() {
        return (client) -> {
            String token = null;
            for (Entry<String,String> header : client.getHandshakeData().getHttpHeaders()) {
                if ("accessToken".equals(header.getKey())) { // 쿠키 이름이 "accessToken"일 경우
                    token = header.getValue();
                    break;
                }
            }
    
            if (token != null) {
                log.info("Received token : {}", token);
    
                String userId = provider.validate(token);
    
                if (userId != null) {
                    log.info("User [{}] connected", userId);
                    client.set("userId", userId);
                    // String roomId = client.getHandshakeData().getSingleUrlParam("roomId");
                    // client.joinRoom(roomId);
    
                    // String joinMessage = userId + "님이 들어왔습니다.";
                    // server.getRoomOperations(roomId).sendEvent("join_message", joinMessage);
                    // log.info("Socket ID[{}] connected to room[{}]", client.getSessionId().toString(), roomId);
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

            String message = userId + "님이 참여하였습니다.";

            client.joinRoom(roomId.toString());
            ChatMessage chatMessage = new ChatMessage(roomId, "system", message);
            server.getRoomOperations(roomId.toString()).sendEvent("receive_message", chatMessage);
        };
    }
}
