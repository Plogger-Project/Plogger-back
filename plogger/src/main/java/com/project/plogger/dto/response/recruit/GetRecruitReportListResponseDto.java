package com.project.plogger.dto.response.recruit;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.RecruitReport;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.RecruitReportEntity;

import lombok.Getter;

@Getter
public class GetRecruitReportListResponseDto extends ResponseDto {

    private List<RecruitReport> reports;

    private GetRecruitReportListResponseDto(List<RecruitReportEntity> recruitReportEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.reports= RecruitReport.getList(recruitReportEntities);
    }

    public static ResponseEntity<? super GetRecruitReportListResponseDto> success(List<RecruitReportEntity> recruitReportEntities) {
        GetRecruitReportListResponseDto responseBody = new GetRecruitReportListResponseDto(recruitReportEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
