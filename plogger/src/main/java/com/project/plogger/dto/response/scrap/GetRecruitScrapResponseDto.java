package com.project.plogger.dto.response.scrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.RecruitScrapEntity;

import lombok.Getter;

@Getter
public class GetRecruitScrapResponseDto extends ResponseDto{

    private List<String> userIds;

    private GetRecruitScrapResponseDto(List<RecruitScrapEntity> recruitScrapEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        List<String> userIds = new ArrayList<>();
        for (RecruitScrapEntity recruitScrapEntity: recruitScrapEntities) userIds.add(recruitScrapEntity.getUserId());
        this.userIds = userIds;
    }
    
    public static ResponseEntity<GetRecruitScrapResponseDto> success(List<RecruitScrapEntity> recruitScrapEntities) {
        GetRecruitScrapResponseDto responseBody = new GetRecruitScrapResponseDto(recruitScrapEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
