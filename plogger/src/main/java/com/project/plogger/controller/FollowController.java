package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.follow.PostFollowRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.follow.GetFolloweeListResponseDto;
import com.project.plogger.dto.response.follow.GetFollowerListResponseDto;
import com.project.plogger.service.FollowService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping(value = {"", "/"})
    public ResponseEntity<ResponseDto> postFollow(
        @AuthenticationPrincipal String followerId,
        @RequestBody @Valid PostFollowRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> response = followService.postFollow(followerId, requestBody);
        return response;
    };

    @DeleteMapping("/{followeeId}")
    public ResponseEntity<ResponseDto> deleteFollow(
        @AuthenticationPrincipal String followerId,
        @PathVariable("followeeId") String followeeId
    ){
        ResponseEntity<ResponseDto> response = followService.deleteFollow(followerId, followeeId);
        return response;
    };

    @GetMapping("/follower/{followeeId}")
    public ResponseEntity<? super GetFollowerListResponseDto> getFollowerList(
        @PathVariable("followeeId") String followeeId
    ) {
        ResponseEntity<? super GetFollowerListResponseDto> response = followService.getFollowerList(followeeId);
        return response;
    }

    @GetMapping("/followee/{followerId}")
    public ResponseEntity<? super GetFolloweeListResponseDto> getFolloweeList(
        @PathVariable("followerId") String followerId
    ) {
        ResponseEntity<? super GetFolloweeListResponseDto> response = followService.getfolloweeList(followerId);
        return response;
    }
    
}
