package com.project.plogger.dto.response.scrap;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;

import lombok.Getter;

public class GetRecruitScrapResponseDto extends ResponseDto{

    private Integer recruitId;
    private List<String> userIds;

    public GetRecruitScrapResponseDto(Integer recruitId, List<String> userIds) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.recruitId = recruitId;
        this.userIds = userIds;
    }
    
    // public static ResponseEntity<GetRecruitScrapResponseDto> {

    // }
}
