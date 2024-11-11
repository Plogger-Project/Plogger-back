package com.project.plogger.common.object;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.plogger.entity.chat.ChatMessageEntity;

import lombok.Getter;

@Getter
public class ChatMessage {
    
    private Integer chatId;
    private String senderId;
    private Integer roomId;
    private String message;
    private String sentAt;

    @JsonCreator
    public ChatMessage(
            @JsonProperty("roomId") Integer roomId,
            @JsonProperty("senderId") String senderId,
            @JsonProperty("message") String message) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.roomId = roomId;
        this.senderId = senderId;
        this.message = message;
        this.sentAt = simpleDateFormat.format(now);;
    }

    public ChatMessage(ChatMessageEntity chatMessageEntity) {
        this.chatId = chatMessageEntity.getChatId();
        this.senderId = chatMessageEntity.getSenderId();
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
