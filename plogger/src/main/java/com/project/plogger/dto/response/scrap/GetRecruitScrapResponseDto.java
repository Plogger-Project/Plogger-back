package com.project.plogger.dto.response.scrap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.RecruitScrapEntity;

import lombok.Getter;

@Getter
public class GetRecruitScrapResponseDto extends ResponseDto{

    private String userId;
    private Integer recruitId;
    private String createdAt;

    public GetRecruitScrapResponseDto(RecruitScrapEntity recruitScrapEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = recruitScrapEntity.getUserId();
        this.recruitId = recruitScrapEntity.getRecruitId();
        this.createdAt = recruitScrapEntity.getCreatedAt();

    }
    
    public static ResponseEntity<GetRecruitScrapResponseDto> success(RecruitScrapEntity recruitScrapEntity) {
        GetRecruitScrapResponseDto responseBody = new GetRecruitScrapResponseDto(recruitScrapEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
