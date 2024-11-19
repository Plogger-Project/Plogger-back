package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ResponseDto> postChatRoom(@RequestBody @Valid PostChatRoomRequestDto dto, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = chatService.createChatRoom(dto, userId);
        return response;
    }

    @GetMapping("/messages")
    public ResponseEntity<? super GetMessageListResponseDto> getTotalChatMessages(
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super GetMessageListResponseDto> response = chatService.getTotalChatMessages(userId);
        return response;
    }

    @GetMapping("/rooms")
    public ResponseEntity<? super GetRoomListResponseDto> getMyChatRooms(@AuthenticationPrincipal String userId) {
        ResponseEntity<? super GetRoomListResponseDto> response = chatService.getMyRooms(userId);
        return response;
    }

}

