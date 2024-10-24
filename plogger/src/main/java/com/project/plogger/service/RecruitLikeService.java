package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseDto;

public interface RecruitLikeService {

    ResponseEntity<ResponseDto> recruitLike(String userId, Integer recruitId); 
    
}
