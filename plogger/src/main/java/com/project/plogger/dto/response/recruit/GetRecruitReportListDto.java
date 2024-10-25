package com.project.plogger.dto.response.recruit;

import com.project.plogger.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class GetRecruitReportListDto extends ResponseDto {

    public GetRecruitReportListDto(String code, String message) {
        super(code, message);

    }
}
