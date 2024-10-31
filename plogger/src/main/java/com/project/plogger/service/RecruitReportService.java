package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.recruit.RecruitReportRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitReportListResponseDto;

public interface RecruitReportService {
    
    ResponseEntity<ResponseDto> createReport(Integer recruitId, String userId, RecruitReportRequestDto dto);

    ResponseEntity<? super GetRecruitReportListResponseDto> getAllRecruitReportPost();
}


