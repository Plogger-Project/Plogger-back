package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.active.ActiveReportRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.active.GetActiveReportListResponseDto;
import com.project.plogger.service.ActiveReportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/report/active")
public class ActiveReportController {
    
    private final ActiveReportService activeReportService;

    @PostMapping("/{activeId}")
    public ResponseEntity<ResponseDto> createReport(
            @PathVariable("activeId") Integer activeId, @AuthenticationPrincipal String userId,
            @RequestBody @Valid ActiveReportRequestDto dto) {
        ResponseEntity<ResponseDto> response = activeReportService.createReport(activeId, userId, dto);
                
        return response;
    }

    @GetMapping("")
    public ResponseEntity<? super GetActiveReportListResponseDto> getAllActiveReportPost() {
        ResponseEntity<? super GetActiveReportListResponseDto> response = activeReportService.getAllActiveReportPost();
        return response;
    }  
}
