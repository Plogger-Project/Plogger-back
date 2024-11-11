package com.project.plogger.service.Implement;

import java.util.List;
import java.util.ArrayList;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.alert.AlertRequestDto;
import com.project.plogger.dto.response.ResponseDto;

import com.project.plogger.dto.response.alert.AlertResponseDto;
import com.project.plogger.dto.response.alert.GetAlertListResponseDto;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.entity.alert.AlertEntity;
import com.project.plogger.repository.AlertRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.AlertService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AlertImplement implements AlertService {

    private final AlertRepository alertRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super AlertResponseDto> getAlertsByUserId(String userId) {

        AlertEntity alertEntity = null;

        try {

            alertEntity = alertRepository.findByUserId(userId);
            if (alertEntity == null) return AlertResponseDto.noExistAlertsFound();


        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();

        }
        return AlertResponseDto.success(alertEntity);
    }

    @Override
    public ResponseEntity<ResponseDto> createAlert(AlertRequestDto dto) {

        try {

            UserEntity userEntity = userRepository.findByUserId(dto.getUserId());
            if(userEntity == null) return ResponseDto.noExistUserId();

            AlertEntity alertEntity = new AlertEntity(dto);
            alertEntity.setUserId(dto.getUserId());

            alertRepository.save(alertEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> markAlertAsRead(Long id) {

        try {
            AlertEntity alert = alertRepository.findById(id).orElse(null);
            if (alert == null) {
                return ResponseDto.noExistAlertsFound();
            }
            alert.setRead(true);
            alertRepository.save(alert);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> deleteAlert(Long id) {

        try {

            if (!alertRepository.existsById(id)) {
                return ResponseDto.noExistAlertsFound();
            }
            alertRepository.deleteById(id);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetAlertListResponseDto> getAlertList(String userId) {

        List<AlertEntity> alertEntities  = new ArrayList<>();

        try {
            alertEntities = alertRepository.findByUserIdOrderByIdDesc(userId);
        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetAlertListResponseDto.success(alertEntities);
    }
}
