package com.project.plogger.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.chat.ChatRoomEntity;

import jakarta.transaction.Transactional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Integer> {
    
    boolean existsByRoomId(Integer roomId);
    ChatRoomEntity findByRoomId(Integer roomId);
    @Transactional
    void deleteByRoomId(Integer roomId);

}
