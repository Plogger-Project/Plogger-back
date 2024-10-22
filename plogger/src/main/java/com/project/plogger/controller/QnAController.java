package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.qna.PatchQnARequestDto;
import com.project.plogger.dto.request.qna.PostQnARequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.qna.GetQnAListResponseDto;
import com.project.plogger.dto.response.qna.GetQnAResponseDto;
import com.project.plogger.service.QnAService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/qna")
@RequiredArgsConstructor
public class QnAController {

    private final QnAService qnaService;

    @PostMapping(value = {"", "/"})
    public ResponseEntity<ResponseDto> postQnA(
        @RequestBody @Valid PostQnARequestDto requestBody,
        @AuthenticationPrincipal String userId
    ){
        ResponseEntity<ResponseDto> response = qnaService.postQnA(requestBody, userId);
        return response;
    };

    @GetMapping(value = {"", "/"})
    public ResponseEntity<? super GetQnAListResponseDto> getQnAList(){
        ResponseEntity<? super GetQnAListResponseDto> response = qnaService.getQnAList();
        return response;
    };

    @GetMapping("/{qnaPostId}")
    public ResponseEntity<? super GetQnAResponseDto> getQnA(
        @PathVariable("qnaPostId") Integer qnaPostId
    ){
        ResponseEntity<? super GetQnAResponseDto> response = qnaService.getQna(qnaPostId);
        return response;
    };

    @PatchMapping("/{qnaPostId}")
    public ResponseEntity<ResponseDto> patchQnA(
        @PathVariable("qnaPostId") Integer qnaPostId,
        @RequestBody @Valid PatchQnARequestDto requestBody
    ){
        ResponseEntity<ResponseDto> response = qnaService.patchQnA(qnaPostId, requestBody);
        return response;
    };
    
}
