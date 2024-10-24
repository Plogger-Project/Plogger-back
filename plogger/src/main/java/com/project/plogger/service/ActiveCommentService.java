package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.active.PostActiveCommentRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.active.GetActiveCommentListResponseDto;

public interface ActiveCommentService {

    ResponseEntity<ResponseDto> postActiveComment(PostActiveCommentRequestDto dto, String userId, Integer activeId);
    ResponseEntity<? super GetActiveCommentListResponseDto> getActiveCommentList(Integer activeId);

}
