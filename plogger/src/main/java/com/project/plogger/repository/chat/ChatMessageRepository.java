package com.project.plogger.repository.chat;

import org.springframework.stereotype.Repository;

import com.project.plogger.entity.chat.ChatMessageEntity;
import com.project.plogger.repository.resultset.GetChatMessageResultSet;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Integer> {
    
    List<ChatMessageEntity> findByRoomId(Integer roomId);

    @Query(
    value=
    "select " +
        "cm.chat_id chatId, " +
        "cm.sender_id senderId, " +
        "cm.room_id roomId, " +
        "cm.message message, " +
        "cm.sent_at as sentAt, " +
        "IF(cr.user_id IS NOT NULL, 1, 0) as isRead " +
    "from chat_message cm left join (SELECT * FROM chat_read WHERE user_id = :userId) cr " +
    "ON cm.chat_id = cr.message_id " +
    "where cm.room_id = :roomId",
    nativeQuery=true
    )
    List<GetChatMessageResultSet> getChatMessageList(@Param("userId") String userId, @Param("roomId") Integer roomId);

    @Query(
    value=
    "select " +
        "cm.chat_id chatId, " +
        "cm.sender_id senderId, " +
        "cm.room_id roomId, " +
        "cm.message message, " +
        "cm.sent_at as sentAt, " +
        "IF(cr.user_id IS NOT NULL, 1, 0) as isRead " +
    "from chat_message cm left join (SELECT * FROM chat_read WHERE user_id = :userId) cr " +
    "ON cm.chat_id = cr.message_id " +
    "where cm.room_id IN (SELECT room_id FROM chat_join WHERE user_id = :userId)",
    nativeQuery=true
    )
    List<GetChatMessageResultSet> getChatMessageList(@Param("userId") String userId);

}
