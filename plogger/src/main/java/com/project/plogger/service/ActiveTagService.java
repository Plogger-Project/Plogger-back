package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.active.PostActiveTagRequestDto;
import com.project.plogger.dto.response.ResponseDto;

public interface ActiveTagService {
    
    ResponseEntity<ResponseDto> postTag(PostActiveTagRequestDto dto, Integer activeId, Integer recruitId);
    ResponseEntity<ResponseDto> deleteTag(PostActiveTagRequestDto dto, Integer activeId, Integer recruitId);

}
