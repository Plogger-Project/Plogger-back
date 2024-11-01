package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.mileage.GetMileageListResponseDto;

public interface MileageService {

    ResponseEntity<ResponseDto> postUpMileage(String userId, Integer activeId);
    ResponseEntity<ResponseDto> postDownMileage(String userId, Integer gifticonId);
    ResponseEntity<ResponseDto> postTagRemoveMileage(String userId, Integer activeId);
    ResponseEntity<? super GetMileageListResponseDto> getMileageList(String userId);
    
}
