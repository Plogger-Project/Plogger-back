package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.like.GetRecruitLikeResponseDto;
import com.project.plogger.service.RecruitLikeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recruit/like")
@RequiredArgsConstructor
public class RecruitLikeController {

    private final RecruitLikeService recruitLikeService;
    
    @PostMapping("/{recruitId}")
    public ResponseEntity<ResponseDto> recruitLike(@AuthenticationPrincipal String userId, @PathVariable("recruitId") Integer recruitId) {
        ResponseEntity<ResponseDto> response = recruitLikeService.recruitLike(userId, recruitId);
        return response;
    }

    @GetMapping("/{recruitId}")
    public ResponseEntity<? super GetRecruitLikeResponseDto> getRecruitLike(
        @PathVariable("recruitId") Integer recruitId 
    ){
        ResponseEntity<? super GetRecruitLikeResponseDto> response = recruitLikeService.getLike(recruitId);
        return response;
    };

}
