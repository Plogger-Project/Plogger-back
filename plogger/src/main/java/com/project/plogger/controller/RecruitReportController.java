package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.recruit.RecruitReportRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitReportListResponseDto;
import com.project.plogger.service.RecruitReportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/report/recruit")
public class RecruitReportController {

    private final RecruitReportService recruitReportService;

    @PostMapping("/{recruitId}")
    public ResponseEntity<ResponseDto> createReport(
            @PathVariable("recruitId") Integer recruitId, @AuthenticationPrincipal String userId,
            @RequestBody @Valid RecruitReportRequestDto dto) {
        ResponseEntity<ResponseDto> response = recruitReportService.createReport(recruitId, userId, dto);

        return response;
    }

    @GetMapping("")
    public ResponseEntity<? super GetRecruitReportListResponseDto> getAllRecruitReportPost() {
        ResponseEntity<? super GetRecruitReportListResponseDto> response = recruitReportService.getAllRecruitReportPost();
        return response;
    }
}
