package com.project.plogger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.alert.AlertRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.admin.GetSignInResponseDto;
import com.project.plogger.dto.response.alert.AlertResponseDto;
import com.project.plogger.entity.alert.AlertEntity;
import com.project.plogger.service.AlertService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/alert")
public class AlertController {

    private final AlertService alertService;

    @GetMapping("/{userId}")
    public ResponseEntity<? super AlertResponseDto> getAlerts(@PathVariable String userId) {
        ResponseEntity<? super AlertResponseDto> response = alertService.getAlertsByUserId(userId);
        return response;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createAlert(@RequestBody @Valid AlertRequestDto request) {
        AlertEntity alert = new AlertEntity();
        alert.setUserId(request.getUserId());
        alert.setMessage(request.getMessage());
        alert.setRead(false);
        return alertService.createAlert(alert);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<ResponseDto> markAsRead(@PathVariable Long id) {
        return alertService.markAlertAsRead(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteAlert(@PathVariable Long id) {
        return alertService.deleteAlert(id);
    }

}
