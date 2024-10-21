package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.qna.PostQnARequestDto;
import com.project.plogger.dto.response.ResponseDto;

public interface QnAService {

    ResponseEntity<ResponseDto> postQnA(PostQnARequestDto dto, String userId);
    
}
