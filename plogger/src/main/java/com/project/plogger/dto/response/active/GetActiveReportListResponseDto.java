package com.project.plogger.dto.response.active;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.ActiveReport;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.ActiveReportEntity;

import lombok.Getter;

@Getter
public class GetActiveReportListResponseDto extends ResponseDto{

    private List<ActiveReport> reports;

    private GetActiveReportListResponseDto(List<ActiveReportEntity> activeReportEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.reports= ActiveReport.getList(activeReportEntities);
    }

    public static ResponseEntity<? super GetActiveReportListResponseDto> success(
            List<ActiveReportEntity> activeReportEntities) {
        GetActiveReportListResponseDto responseBody = new GetActiveReportListResponseDto(activeReportEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
