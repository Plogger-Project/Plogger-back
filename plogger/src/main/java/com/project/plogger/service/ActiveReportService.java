package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.active.ActiveReportRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.active.GetActiveReportListResponseDto;

public interface ActiveReportService {
    
    ResponseEntity<ResponseDto> createReport(Integer activeId, String userId, ActiveReportRequestDto dto);

    ResponseEntity<? super GetActiveReportListResponseDto> getAllActiveReportPost();
}
