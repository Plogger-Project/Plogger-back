package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseDto;

public interface RecruitJoinService {
    
    ResponseEntity<ResponseDto> recruitJoin(String userId, Integer recruitId);

}
