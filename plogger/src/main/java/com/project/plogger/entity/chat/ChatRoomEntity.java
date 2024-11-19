package com.project.plogger.entity.chat;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.plogger.dto.request.chat.PostChatRoomRequestDto;

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
@Table(name = "chat_room")
@Entity(name = "chat_room")
public class ChatRoomEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;
    private String roomName;
    private String createdAt;

    public ChatRoomEntity(PostChatRoomRequestDto dto) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.roomName = dto.getRoomName();
        this.createdAt = simpleDateFormat.format(now);
    }

    public ChatRoomEntity(String roomName) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.roomName = roomName;
        this.createdAt = simpleDateFormat.format(now);
    }

}
