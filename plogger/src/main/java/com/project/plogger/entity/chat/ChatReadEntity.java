package com.project.plogger.entity.chat;

import com.project.plogger.entity.pk.ChatReadPk;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "chat_read")
@Table(name = "chat_read")
@IdClass(ChatReadPk.class)
public class ChatReadEntity {
    
    @Id
    private String userId;
    @Id
    private Integer messageId;

}
