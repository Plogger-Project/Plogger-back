package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.mileage.PostMileageDownRequestDto;
import com.project.plogger.dto.request.mileage.PostMileageUpRequestDto;
import com.project.plogger.dto.response.ResponseDto;

public interface MileageService {

    ResponseEntity<ResponseDto> postMileage(PostMileageUpRequestDto dto, String userId, Integer activeId);
    ResponseEntity<ResponseDto> postMileage(PostMileageDownRequestDto dto, String userId, Integer gifticonId);
    
}
