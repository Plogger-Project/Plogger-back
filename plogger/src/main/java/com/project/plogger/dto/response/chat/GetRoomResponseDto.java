package com.project.plogger.dto.response.chat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.chat.ChatRoomEntity;

import lombok.Getter;

@Getter
public class GetRoomResponseDto extends ResponseDto {
    
    private Integer roomId;
    private String roomName;
    private String createdAt;

    public GetRoomResponseDto(ChatRoomEntity chatRoomEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.roomId = chatRoomEntity.getRoomId();
        this.roomName = chatRoomEntity.getRoomName();
        this.createdAt = chatRoomEntity.getCreatedAt();
    }

    public static ResponseEntity<GetRoomResponseDto> success(ChatRoomEntity chatRoomEntity) {
        GetRoomResponseDto responseBody = new GetRoomResponseDto(chatRoomEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

}