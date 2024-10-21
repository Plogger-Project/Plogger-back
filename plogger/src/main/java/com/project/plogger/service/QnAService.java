package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.qna.PostQnARequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.qna.GetQnAListResponseDto;

public interface QnAService {

    ResponseEntity<ResponseDto> postQnA(PostQnARequestDto dto, String userId);
    ResponseEntity<? super GetQnAListResponseDto> getQnAList();
    // ResponseEntity<ResponseDto> patchTool(Integer toolNumber, PatchToolRequestDto dto);
    
}
