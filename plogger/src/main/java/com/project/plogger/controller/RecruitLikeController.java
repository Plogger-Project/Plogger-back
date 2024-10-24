package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.service.RecruitLikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recruit")
@RequiredArgsConstructor
public class RecruitLikeController {

    private final RecruitLikeService recruitLikeService;
    
    @PostMapping("/like/{recruitId}")
    public ResponseEntity<ResponseDto> recruitLike(@AuthenticationPrincipal String userId, @PathVariable Integer recruitId) {
        ResponseEntity<ResponseDto> response = recruitLikeService.recruitLike(userId, recruitId);
        return response;
    }

}
