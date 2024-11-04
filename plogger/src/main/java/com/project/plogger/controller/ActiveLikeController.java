package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.service.ActiveLikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/active")
@RequiredArgsConstructor
public class ActiveLikeController {

    private final ActiveLikeService activeLikeService;
    
    @PostMapping("/like/{activeId}")
    public ResponseEntity<ResponseDto> activeLike(@AuthenticationPrincipal String userId, @PathVariable("activeId") Integer activeId) {
        ResponseEntity<ResponseDto> response = activeLikeService.activeLike(userId, activeId);
        return response;
    }

}
