package com.project.plogger.entity.chat;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.plogger.entity.pk.ChatJoinPk;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chat_join")
@Entity(name = "chat_join")
@IdClass(ChatJoinPk.class)
public class ChatJoinEntity {
    
    @Id
    private Integer roomId;
    @Id
    private String userId;
    private String joinedAt;

    public ChatJoinEntity(Integer roomId, String userId) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.joinedAt = simpleDateFormat.format(now);
    }

}
