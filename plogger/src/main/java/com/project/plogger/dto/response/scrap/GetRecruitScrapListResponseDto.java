package com.project.plogger.dto.response.scrap;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.Recruit;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.RecruitEntity;

import lombok.Getter;

@Getter
public class GetRecruitScrapListResponseDto extends ResponseDto{

    private List<Recruit> scraps;

    private GetRecruitScrapListResponseDto(List<RecruitEntity> entities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.scraps = Recruit.getList(entities);
    }

    public static ResponseEntity<GetRecruitScrapListResponseDto> success(List<RecruitEntity> entities) {
        GetRecruitScrapListResponseDto responseBody = new GetRecruitScrapListResponseDto(entities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
