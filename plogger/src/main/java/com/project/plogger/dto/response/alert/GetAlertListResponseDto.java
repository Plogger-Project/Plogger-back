package com.project.plogger.dto.response.alert;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.Alert;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.alert.AlertEntity;

import lombok.Getter;

@Getter
public class GetAlertListResponseDto extends ResponseDto {
    

    private List<Alert> Alerts;

    public GetAlertListResponseDto(List<AlertEntity> alertEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.Alerts = Alert.getList(alertEntities);
    }

    public static ResponseEntity<GetAlertListResponseDto> success(List<AlertEntity> alertEntities) {
        GetAlertListResponseDto responseBody = new GetAlertListResponseDto(alertEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
