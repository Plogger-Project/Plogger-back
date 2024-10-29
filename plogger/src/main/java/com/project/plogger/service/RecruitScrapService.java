package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseDto;

public interface RecruitScrapService {
    
    ResponseEntity<ResponseDto> recruitScrap(String userId, Integer recruitId);
    
}
