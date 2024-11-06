package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.chat.PostChatMessageRequestDto;
import com.project.plogger.dto.request.chat.PostChatRoomRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.chat.GetMessageListResponseDto;
import com.project.plogger.dto.response.chat.GetRoomListResponseDto;
import com.project.plogger.dto.response.chat.GetRoomResponseDto;

public interface ChatService {

    ResponseEntity<ResponseDto> createChatRoom(PostChatRoomRequestDto dto);
    ResponseEntity<? super GetMessageListResponseDto> getMessages(Integer roomId);
    ResponseEntity<ResponseDto> saveMessage(PostChatMessageRequestDto dto, Integer roomId, String senderId);
    ResponseEntity<ResponseDto> joinRoom(Integer roomId, String userId);
    ResponseEntity<? super GetRoomResponseDto> getRoom(Integer roomId);
    ResponseEntity<? super GetRoomListResponseDto> getMyRooms(String userId);
    
}
