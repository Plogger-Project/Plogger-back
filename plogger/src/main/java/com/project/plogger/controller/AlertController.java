package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.alert.AlertRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.alert.GetAlertListResponseDto;
import com.project.plogger.service.AlertService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/alert")
public class AlertController {

    private final AlertService alertService;

    @PostMapping
    public ResponseEntity<ResponseDto> createAlert(
        @RequestBody @Valid AlertRequestDto request,
        @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = alertService.createAlert(request, userId);
        return response;
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<ResponseDto> markAsRead(@PathVariable Long id) {
        return alertService.markAlertAsRead(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteAlert(@PathVariable Long id) {
        return alertService.deleteAlert(id);
    }


    @GetMapping(value = {"", "/"})
    public ResponseEntity<? super GetAlertListResponseDto> getAlerts(@AuthenticationPrincipal String userId) {
        ResponseEntity<? super GetAlertListResponseDto> response = alertService.getAlertList(userId);
        return response;
    }
}