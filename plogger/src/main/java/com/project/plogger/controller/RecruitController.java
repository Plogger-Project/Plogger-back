package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.recruit.PostRecruitRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitResponseDto;
import com.project.plogger.service.RecruitService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recruit")
@RequiredArgsConstructor
public class RecruitController {

    private final RecruitService recruitService;

    @PostMapping(value = { "", "/" })
    public ResponseEntity<ResponseDto> postRecruit(@RequestBody @Valid PostRecruitRequestDto dto,
            @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = recruitService.postRecruit(dto, userId);
        return response;
    }

    @GetMapping(value = {"/{recruitPostId}"})
    public ResponseEntity<? super GetRecruitResponseDto> getRecruit(
        @PathVariable("recruitPostId") Integer recruitPostId
    ) {
        ResponseEntity<? super GetRecruitResponseDto> response = recruitService.getRecruit(recruitPostId);
        return response;
    }
    
}
