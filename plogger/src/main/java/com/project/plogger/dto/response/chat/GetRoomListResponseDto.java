package com.project.plogger.dto.response.chat;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.ChatRoom;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.chat.ChatRoomEntity;

import lombok.Getter;

@Getter
public class GetRoomListResponseDto extends ResponseDto {
    
    private List<ChatRoom> rooms;

    private GetRoomListResponseDto(List<ChatRoomEntity> chatRoomEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.rooms = ChatRoom.getList(chatRoomEntities);
    }

    public static ResponseEntity<GetRoomListResponseDto> success(List<ChatRoomEntity> chatRoomEntities) {
        GetRoomListResponseDto responseBody = new GetRoomListResponseDto(chatRoomEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

}