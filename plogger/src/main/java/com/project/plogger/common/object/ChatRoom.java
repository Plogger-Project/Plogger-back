package com.project.plogger.common.object;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonCreator
    public ChatRoom(@JsonProperty("roomName") String roomName) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.roomName = roomName;
        this.createdAt = simpleDateFormat.format(now);
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
