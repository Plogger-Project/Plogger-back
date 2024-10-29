package com.project.plogger.service.Implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.alert.AlertResponseDto;
import com.project.plogger.entity.alert.AlertEntity;
import com.project.plogger.repository.AlertRepository;
import com.project.plogger.service.AlertService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AlertImplement implements AlertService {

    private AlertRepository alertRepository;

    @Autowired
    public AlertImplement(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public ResponseEntity<? super AlertResponseDto> getAlertsByUserId(String userId) {

        try {

            List<AlertEntity> alerts = alertRepository.findByUserId(userId);
            if (alerts == null) return ResponseDto.noExistAlertsFound();

        } catch (Exception exception) {

            exception.printStackTrace();
            return ResponseDto.databaseError();

        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> createAlert(AlertEntity alert) {

        try {
            alertRepository.save(alert);

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
    
}
