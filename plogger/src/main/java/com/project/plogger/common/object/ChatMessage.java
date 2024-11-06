package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.chat.ChatMessageEntity;

import lombok.Getter;

@Getter
public class ChatMessage {
    
    private Integer chatId;
    private String senderId;
    private String receiverId;
    private Integer roomId;
    private String message;
    private String sentAt;

    public ChatMessage(ChatMessageEntity chatMessageEntity) {
        this.chatId = chatMessageEntity.getChatId();
        this.senderId = chatMessageEntity.getSenderId();
        this.receiverId = chatMessageEntity.getReceiverId();
        this.roomId = chatMessageEntity.getRoomId();
        this.message = chatMessageEntity.getMessage();
        this.sentAt = chatMessageEntity.getSendAt();
    }

    public static List<ChatMessage> getList(List<ChatMessageEntity> chatMessageEntities) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        for (ChatMessageEntity chatMessageEntity : chatMessageEntities) {
            ChatMessage chatMessage = new ChatMessage(chatMessageEntity);
            chatMessages.add(chatMessage);
        }

        return chatMessages;
    }

}
