package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.active.PostActiveCommentRequestDto;
import com.project.plogger.dto.response.ResponseDto;

public interface ActiveCommentService {

    ResponseEntity<ResponseDto> postActiveComment(PostActiveCommentRequestDto dto, String userId, Integer activeId);
    
}
