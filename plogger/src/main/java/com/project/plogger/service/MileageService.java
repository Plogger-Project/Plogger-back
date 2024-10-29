package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseDto;

public interface MileageService {

    ResponseEntity<ResponseDto> postUpMileage(String userId, Integer activeId);
    ResponseEntity<ResponseDto> postDownMileage(String userId, Integer gifticonId);
    
}