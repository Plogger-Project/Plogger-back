package com.project.plogger.dto.response.alert;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.alert.AlertEntity;

import lombok.Getter;

@Getter
public class AlertResponseDto extends ResponseDto {

    private Long id;
    private String userId;
    private String message;
    private Boolean isRead;
    private String createdAt;

    public AlertResponseDto(AlertEntity alertEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.id = alertEntity.getId();
        this.userId = alertEntity.getUserId();
        this.message = alertEntity.getMessage();
        this.isRead = alertEntity.isRead();
        this.createdAt = alertEntity.getCreatedAt();
    }

    public static ResponseEntity<AlertResponseDto> success(AlertEntity alertEntity) {
        AlertResponseDto responseBody = new AlertResponseDto(alertEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
