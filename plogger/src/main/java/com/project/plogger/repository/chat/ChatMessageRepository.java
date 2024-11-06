package com.project.plogger.repository.chat;

import org.springframework.stereotype.Repository;

import com.project.plogger.entity.chat.ChatMessageEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Integer> {
    
    List<ChatMessageEntity> findByRoomId(Integer roomId);

}
