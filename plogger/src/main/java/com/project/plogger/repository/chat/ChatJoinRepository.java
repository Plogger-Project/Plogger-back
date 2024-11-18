package com.project.plogger.repository.chat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.chat.ChatJoinEntity;
import com.project.plogger.entity.pk.ChatJoinPk;

import jakarta.transaction.Transactional;

@Repository
public interface ChatJoinRepository extends JpaRepository<ChatJoinEntity, ChatJoinPk> {
    
    List<ChatJoinEntity> findByUserId(String userId);
    boolean existsByRoomIdAndUserId(Integer roomId, String userId);
    @Transactional
    void deleteByRoomIdAndUserId(Integer roomId, String userId);
    boolean existsByRoomId(Integer roomId);

}
