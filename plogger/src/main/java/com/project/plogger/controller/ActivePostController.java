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

import com.project.plogger.dto.request.active.PatchActiveCommentRequestDto;
import com.project.plogger.dto.request.active.PatchActivePostRequestDto;
import com.project.plogger.dto.request.active.PostActiveCommentRequestDto;
import com.project.plogger.dto.request.active.PostActivePostRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.active.GetActiveCommentListResponseDto;
import com.project.plogger.dto.response.active.GetActivePostListResponseDto;
import com.project.plogger.dto.response.active.GetActivePostResponseDto;
import com.project.plogger.dto.response.active.GetMyRecruitPostListResponseDto;
import com.project.plogger.service.ActiveCommentService;
import com.project.plogger.service.ActivePostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/active")
@RequiredArgsConstructor
public class ActivePostController {

    private final ActivePostService activePostService;
    private final ActiveCommentService activeCommentService;
    // private final MileageService mileageService;
    
    @PostMapping("/{recruitId}")
    public ResponseEntity<ResponseDto> postActivePost(
        @RequestBody @Valid PostActivePostRequestDto dto, 
        @AuthenticationPrincipal String userId, @PathVariable("recruitId") Integer recruitId) {
        ResponseEntity<ResponseDto> response = activePostService.postActivePost(dto, userId, recruitId);
        return response;
    }

    // @PostMapping("/{activePostId}")
    // public ResponseEntity<ResponseDto> postActivePost(
    //         @AuthenticationPrincipal String userId,
    //         @PathVariable("activePostId") Integer activePostId) {
    //     ResponseEntity<ResponseDto> response = mileageService.postUpMileage(userId, activePostId);
    //     return response;
    // }

    @PatchMapping("/{activePostId}")
    public ResponseEntity<ResponseDto> patchActivePost(@RequestBody @Valid PatchActivePostRequestDto dto, @PathVariable Integer activePostId, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = activePostService.patchActivePost(dto, activePostId, userId);
        return response;
    }

    @DeleteMapping("/{activePostId}")
    public ResponseEntity<ResponseDto> deleteActivePost(@PathVariable Integer activePostId, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = activePostService.deleteActivePost(activePostId, userId);
        return response;
    }

    @GetMapping("/{activePostId}")
    public ResponseEntity<? super GetActivePostResponseDto> getActivePost(@PathVariable Integer activePostId) {
        ResponseEntity<? super GetActivePostResponseDto> response = activePostService.getActivePost(activePostId);
        return response;
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<? super GetActivePostListResponseDto> getActivePosts() {
        ResponseEntity<? super GetActivePostListResponseDto> response = activePostService.getActivePostList();
        return response;
    }

    @GetMapping("/my-recruits")
    public ResponseEntity<? super GetMyRecruitPostListResponseDto> getMyRecruitPosts(@AuthenticationPrincipal String userId) {
        ResponseEntity<? super GetMyRecruitPostListResponseDto> response = activePostService.getMyRecruitPosts(userId);
        return response;
    }

    @PostMapping("/{activePostId}/comments")
    public ResponseEntity<ResponseDto> postActiveComment(
        @RequestBody @Valid PostActiveCommentRequestDto requestBody,
        @AuthenticationPrincipal String userId,
        @PathVariable("activePostId") Integer activePostId
    ){ 
        ResponseEntity<ResponseDto> response = activeCommentService.postActiveComment(requestBody, userId, activePostId);
        return response;
    }

    @GetMapping("/{activePostId}/comments")
    public ResponseEntity<? super GetActiveCommentListResponseDto> getActiveCommentList(
        @PathVariable("activePostId") Integer activePostId
    ){
        ResponseEntity<? super GetActiveCommentListResponseDto> response = activeCommentService.getActiveCommentList(activePostId);
        return response;
    }

    @PatchMapping("/{activePostId}/comments/{activeCommentId}")
    public ResponseEntity<ResponseDto> patchActiveComment(
        @PathVariable("activePostId") Integer activePostId,
        @PathVariable("activeCommentId") Integer activeCommentId,
        @AuthenticationPrincipal String userId,
        @RequestBody @Valid PatchActiveCommentRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> response = activeCommentService.patchActiveComment(activePostId, activeCommentId, userId, requestBody);
        return response;
    };

    @DeleteMapping("/{activePostId}/comments/{activeCommentId}")
    public ResponseEntity<ResponseDto> deleteActiveComment(
        @PathVariable("activePostId") Integer activePostId,
        @PathVariable("activeCommentId") Integer activeCommentId,
        @AuthenticationPrincipal String userId
    ){
        ResponseEntity<ResponseDto> response = activeCommentService.deleteActiveComment(activePostId, activeCommentId, userId);
        return response;
    };

}
