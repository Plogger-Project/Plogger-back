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

import com.project.plogger.dto.request.qna.PatchQnARequestDto;
import com.project.plogger.dto.request.qna.PostQnACommentRequestDto;
import com.project.plogger.dto.request.qna.PostQnARequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.qna.GetQnACommentListResponseDto;
import com.project.plogger.dto.response.qna.GetQnAListResponseDto;
import com.project.plogger.dto.response.qna.GetQnAResponseDto;
import com.project.plogger.service.QnACommentService;
import com.project.plogger.service.QnAService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/qna")
@RequiredArgsConstructor
public class QnAController {

    private final QnAService qnaService;
    private final QnACommentService qnaCommentService;

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
        @AuthenticationPrincipal String userId,
        @RequestBody @Valid PatchQnARequestDto requestBody
    ){
        ResponseEntity<ResponseDto> response = qnaService.patchQnA(qnaPostId, userId, requestBody);
        return response;
    };

    @DeleteMapping("/{qnaPostId}")
    public ResponseEntity<ResponseDto> deleteQnA(
        @PathVariable("qnaPostId") Integer qnaPostId,
        @AuthenticationPrincipal String userId
    ){
        ResponseEntity<ResponseDto> response = qnaService.deleteQnA(qnaPostId, userId);
        return response;
    };

    @PostMapping("/{qnaPostId}/comments")
    public ResponseEntity<ResponseDto> postQnAComment(
        @RequestBody @Valid PostQnACommentRequestDto requestBody,
        @AuthenticationPrincipal String userId,
        @PathVariable("qnaPostId") Integer qnaPostId
    ){ 
        ResponseEntity<ResponseDto> response = qnaCommentService.postQnAComment(requestBody, userId, qnaPostId);
        return response;
    }

    @GetMapping("/{qnaPostId}/comments")
    public ResponseEntity<? super GetQnACommentListResponseDto> getQnACommentList(
        @PathVariable("qnaPostId") Integer qnaPostId   
    ){
        ResponseEntity<? super GetQnACommentListResponseDto> response = qnaCommentService.getQnACommentList(qnaPostId);
        return response;
}
    
}
