package com.project.plogger.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.alert.AlertRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.alert.AlertResponseDto;
import com.project.plogger.dto.response.alert.GetAlertListResponseDto;

@Service
public interface AlertService {
    ResponseEntity<? super AlertResponseDto> getAlertsByUserId(String userId);
    ResponseEntity<? super GetAlertListResponseDto> getAlertList(String userId);
    ResponseEntity<ResponseDto> createAlert(AlertRequestDto dto);
    ResponseEntity<ResponseDto> markAlertAsRead(Long id);
    ResponseEntity<ResponseDto> deleteAlert(Long id);
}
