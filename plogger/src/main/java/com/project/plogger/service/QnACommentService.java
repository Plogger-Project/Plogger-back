package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.qna.PostQnACommentRequestDto;
import com.project.plogger.dto.response.ResponseDto;

public interface QnACommentService {

    ResponseEntity<ResponseDto> postQnAComment(PostQnACommentRequestDto dto, String userId, Integer qnaId);
    
}
