package com.project.plogger.dto.response.chat;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.ChatMessage;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.chat.ChatMessageEntity;

import lombok.Getter;

@Getter
public class GetMessageListResponseDto extends ResponseDto {

    private List<ChatMessage> messages;

    private GetMessageListResponseDto(List<ChatMessageEntity> chatMessageEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.messages = ChatMessage.getList(chatMessageEntities);
    }

    public static ResponseEntity<GetMessageListResponseDto> success(List<ChatMessageEntity> chatMessageEntities) {
        GetMessageListResponseDto responseBody = new GetMessageListResponseDto(chatMessageEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
