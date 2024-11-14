package com.project.plogger.common.object;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.plogger.entity.chat.ChatMessageEntity;
import com.project.plogger.repository.resultset.GetChatMessageResultSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    
    private Integer chatId;
    private String senderId;
    private Integer roomId;
    private String message;
    private String sentAt;
    private Boolean isRead;

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
        this.sentAt = simpleDateFormat.format(now);
    }

    public ChatMessage(GetChatMessageResultSet resultSet) {
        this.chatId = resultSet.getChatId();
        this.senderId = resultSet.getSenderId();
        this.roomId = resultSet.getRoomId();
        this.message = resultSet.getMessage();
        this.sentAt = resultSet.getSentAt();
        this.isRead = resultSet.getIsRead() == 1;
    }

    public static List<ChatMessage> getList(List<GetChatMessageResultSet> resultSets) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        for (GetChatMessageResultSet resultSet : resultSets) {
            ChatMessage chatMessage = new ChatMessage(resultSet);
            chatMessages.add(chatMessage);
        }

        return chatMessages;
    }

}
