package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.follow.PostFollowRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.follow.GetFolloweeListResponseDto;
import com.project.plogger.dto.response.follow.GetFollowerListResponseDto;

public interface FollowService {

    ResponseEntity<ResponseDto> postFollow(String followerId, PostFollowRequestDto dto);
    ResponseEntity<ResponseDto> deleteFollow(String followerId, String followeeId);
    ResponseEntity<? super GetFollowerListResponseDto> getFollowerList(String followerId);
    ResponseEntity<? super GetFolloweeListResponseDto> getfolloweeList(String followeeId);

}
