package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.recruit.PostRecruitCommentRequestDto;
import com.project.plogger.dto.response.ResponseDto;

public interface RecruitCommentService {

    ResponseEntity<ResponseDto> postRecruitComment(PostRecruitCommentRequestDto dto, String userId, Integer recruitId);
    
}
