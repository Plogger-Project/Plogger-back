package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.project.plogger.dto.request.recruit.PatchRecruitCommentRequestDto;
import com.project.plogger.dto.request.recruit.PatchRecruitRequestDto;
import com.project.plogger.dto.request.recruit.PostRecruitCommentRequestDto;
import com.project.plogger.dto.request.recruit.PostRecruitRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitCommentListResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitListResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitResponseDto;
import com.project.plogger.service.RecruitCommentService;
import com.project.plogger.service.RecruitService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recruit")
@RequiredArgsConstructor
public class RecruitController {

    private final RecruitService recruitService;
    private final RecruitCommentService recruitCommentService;

    @PostMapping(value = { "", "/" })
    public ResponseEntity<ResponseDto> postRecruit(
        @RequestBody @Valid PostRecruitRequestDto dto,
        @AuthenticationPrincipal String userId
    ) {
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
    
    @GetMapping(value = {"","/"})
    public ResponseEntity<? super GetRecruitListResponseDto> getRecruitList() {
        ResponseEntity<? super GetRecruitListResponseDto> response = recruitService.getRecruitList();
        return response;
    }

    @PatchMapping("/{recruitPostId}")
    public ResponseEntity<ResponseDto> patchRecruit(
        @PathVariable("recruitPostId") Integer recruitPostId,
        @AuthenticationPrincipal String userId,
        @RequestBody @Valid PatchRecruitRequestDto requestBody
    ) {
        ResponseEntity<ResponseDto> response = recruitService.patchRecruit(recruitPostId, userId, requestBody);
        return response;
    }

    @DeleteMapping("/{recruitPostId}")
    public ResponseEntity<ResponseDto> deleteRecruit(
        @PathVariable("recruitPostId") Integer recruitPostId,
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<ResponseDto> response = recruitService.deleteRecruit(recruitPostId, userId);
        return response; 
    }

    @PostMapping("/{recruitPostId}/comments")
    public ResponseEntity<ResponseDto> postRecruitComment(
        @RequestBody @Valid PostRecruitCommentRequestDto requestBody,
        @AuthenticationPrincipal String userId,
        @PathVariable("recruitPostId") Integer recruitPostId
    ){ 
        ResponseEntity<ResponseDto> response = recruitCommentService.postRecruitComment(requestBody, userId, recruitPostId);
        return response;
    }

    @GetMapping("/{recruitPostId}/comments")
    public ResponseEntity<? super GetRecruitCommentListResponseDto> getRecruitCommentList(
        @PathVariable("recruitPostId") Integer recruitPostId   
    ){
        ResponseEntity<? super GetRecruitCommentListResponseDto> response = recruitCommentService.getRecruitCommentList(recruitPostId);
        return response;
    }

    @PatchMapping("/{recruitPostId}/comments/{recruitCommentId}")
    public ResponseEntity<ResponseDto> patchQnAComment(
        @PathVariable("recruitPostId") Integer recruitPostId,
        @PathVariable("recruitCommentId") Integer recruitCommentId,
        @AuthenticationPrincipal String userId,
        @RequestBody @Valid PatchRecruitCommentRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> response = recruitCommentService.patchRecruitComment(recruitPostId, recruitCommentId, userId, requestBody);
        return response;
    };

    @DeleteMapping("/{recruitPostId}/comments/{recruitCommentId}")
    public ResponseEntity<ResponseDto> deleteQnAComment(
        @PathVariable("recruitPostId") Integer recruitPostId,
        @PathVariable("recruitCommentId") Integer recruitCommentId,
        @AuthenticationPrincipal String userId
    ){
        ResponseEntity<ResponseDto> response = recruitCommentService.deleteRecruitComment(recruitPostId, recruitCommentId, userId);
        return response;
    };

    
}
