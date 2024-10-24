package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseDto;

public interface TagService {
    
    ResponseEntity<ResponseDto> postTag(String userId, Integer recruitId);
    ResponseEntity<ResponseDto> deleteTag(String userId, Integer recruitId);

}
