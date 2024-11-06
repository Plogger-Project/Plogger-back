package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.chat.ChatRoomEntity;

import lombok.Getter;

@Getter
public class ChatRoom {
    
    private Integer roomId;
    private String roomName;
    private String createdAt;

    public ChatRoom(ChatRoomEntity chatRoomEntity) {
        this.roomId = chatRoomEntity.getRoomId();
        this.roomName = chatRoomEntity.getRoomName();
        this.createdAt = chatRoomEntity.getCreatedAt();
    }

    public static List<ChatRoom> getList(List<ChatRoomEntity> chatRoomEntities) {
        List<ChatRoom> chatRooms = new ArrayList<>();
        for (ChatRoomEntity chatRoomEntity : chatRoomEntities) {
            ChatRoom chatRoom = new ChatRoom(chatRoomEntity);
            chatRooms.add(chatRoom);
        }

        return chatRooms;
    }

}
