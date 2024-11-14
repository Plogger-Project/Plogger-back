package com.project.plogger.entity.chat;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.plogger.dto.request.chat.PostChatMessageRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chat_message")
@Entity(name = "chat_message")
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chatId;
    private String senderId;
    private Integer roomId;
    private String message;
    @Column(name = "sent_at")
    private String sendAt;

    public ChatMessageEntity(PostChatMessageRequestDto dto, String senderId, Integer roomId) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.roomId = roomId;
        this.senderId = senderId;
        this.message = dto.getMessage();
        this.sendAt = simpleDateFormat.format(now);
    }

    public ChatMessageEntity(Integer roomId, String senderId, String message) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.roomId = roomId;
        this.senderId = senderId;
        this.message = message;
        this.sendAt = simpleDateFormat.format(now);
    }
    
}