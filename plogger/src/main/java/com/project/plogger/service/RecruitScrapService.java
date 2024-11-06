package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.scrap.GetRecruitScrapListResponseDto;
import com.project.plogger.dto.response.scrap.GetRecruitScrapResponseDto;

public interface RecruitScrapService {
    
    ResponseEntity<ResponseDto> recruitScrap(String userId, Integer recruitId);
    ResponseEntity<? super GetRecruitScrapListResponseDto> getScrapList();
    ResponseEntity<? super GetRecruitScrapResponseDto> getScrap(Integer recruitId);
    
}
