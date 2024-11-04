package com.project.plogger.dto.response.scrap;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.Scrap;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.RecruitScrapEntity;

import lombok.Getter;

@Getter
public class GetRecruitScrapListResponseDto extends ResponseDto{

    private List<Scrap> scraps;

    private GetRecruitScrapListResponseDto(List<RecruitScrapEntity> scraps) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.scraps = Scrap.getList(scraps);
    }

    public static ResponseEntity<GetRecruitScrapListResponseDto> success(List<RecruitScrapEntity> scraps) {
        GetRecruitScrapListResponseDto responseBody = new GetRecruitScrapListResponseDto(scraps);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
