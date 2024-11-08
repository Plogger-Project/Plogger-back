package com.project.plogger.dto.response.like;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.ActiveLikeEntity;

import lombok.Getter;

@Getter
public class GetActiveLikeResponseDto extends ResponseDto {

    private List<String> userIds;

    private GetActiveLikeResponseDto(List<ActiveLikeEntity> activeLikeEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        List<String> userIds = new ArrayList<>();
        for(ActiveLikeEntity activeLikeEntity: activeLikeEntities) userIds.add(activeLikeEntity.getUserId());
        this.userIds = userIds;
    }

    public static ResponseEntity<GetActiveLikeResponseDto> success(List<ActiveLikeEntity> activeLikeEntities) {
        GetActiveLikeResponseDto responseBody = new GetActiveLikeResponseDto(activeLikeEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
