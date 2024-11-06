package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.chat.PostChatMessageRequestDto;
import com.project.plogger.dto.request.chat.PostChatRoomRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.chat.GetMessageListResponseDto;
import com.project.plogger.dto.response.chat.GetRoomListResponseDto;
import com.project.plogger.service.ChatService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class ChatController {
    
    private final ChatService chatService;

    @PostMapping("/rooms")
    public ResponseEntity<ResponseDto> postChatRoom(@RequestBody @Valid PostChatRoomRequestDto dto) {
        ResponseEntity<ResponseDto> response = chatService.createChatRoom(dto);
        return response;
    }

    @PostMapping("/rooms/{roomId}/messages")
    public ResponseEntity<ResponseDto> postChatMessage(@RequestBody @Valid PostChatMessageRequestDto dto, 
    @PathVariable("roomId") Integer roomId, @AuthenticationPrincipal String senderId) {
        ResponseEntity<ResponseDto> response = chatService.saveMessage(dto, roomId, senderId);
        return response;
    }

    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<? super GetMessageListResponseDto> getChatMessages(@PathVariable("roomId") Integer roomId) {
        ResponseEntity<? super GetMessageListResponseDto> response = chatService.getMessages(roomId);
        return response;
    }

    @PostMapping("/rooms/{roomId}/join")
    public ResponseEntity<ResponseDto> joinChatRoom(@PathVariable("roomId") Integer roomId, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = chatService.joinRoom(roomId, userId);
        return response;
    }

    @GetMapping("/rooms")
    public ResponseEntity<? super GetRoomListResponseDto> getMyChatRooms(@AuthenticationPrincipal String userId) {
        ResponseEntity<? super GetRoomListResponseDto> response = chatService.getMyRooms(userId);
        return response;
    }

}

