package com.project.plogger.repository.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.chat.ChatReadEntity;
import com.project.plogger.entity.pk.ChatReadPk;

@Repository
public interface ChatReadRepository extends JpaRepository<ChatReadEntity, ChatReadPk> {
    
}
