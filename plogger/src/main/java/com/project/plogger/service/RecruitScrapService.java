package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.scrap.PostRecruitScrapRequestDto;
import com.project.plogger.dto.response.ResponseDto;

public interface RecruitScrapService {
    
    ResponseEntity<ResponseDto> postRecruitScrap(PostRecruitScrapRequestDto dto, String userId, String recruitPostId);

}
