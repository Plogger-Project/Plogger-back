package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor

public class AdminController {

    private final AdminService adminService;

    @DeleteMapping("/{recruitId}")
    public ResponseEntity<ResponseDto> deleteRecruitReport(@PathVariable("recruitId") Integer recruitId) {
        ResponseEntity<ResponseDto> response = adminService.deleteRecruitReport(recruitId);
        return response;
    }

    @DeleteMapping("/{activeId}")
    public ResponseEntity<ResponseDto> deleteActiveReport(@PathVariable("activeId") Integer activeId) {
        ResponseEntity<ResponseDto> response = adminService.deleteActiveReport(activeId);
        return response;
    }
}
