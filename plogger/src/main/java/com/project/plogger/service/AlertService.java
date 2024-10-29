package com.project.plogger.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.alert.AlertRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.alert.AlertResponseDto;
import com.project.plogger.entity.alert.AlertEntity;

@Service
public interface AlertService {
    ResponseEntity<? super AlertResponseDto> getAlertsByUserId(String userId);
    ResponseEntity<ResponseDto> createAlert(AlertEntity alert);
    ResponseEntity<ResponseDto> markAlertAsRead(Long id);
    ResponseEntity<ResponseDto> deleteAlert(Long id);
}
