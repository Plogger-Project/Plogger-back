package com.project.plogger.dto.response.follow;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.Followee;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.FollowEntity;

import lombok.Getter;

@Getter
public class GetFolloweeListResponseDto extends ResponseDto {

    private List<Followee> follows;

    public GetFolloweeListResponseDto(List<FollowEntity> followEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.follows = Followee.getList(followEntities);
    }

    public static ResponseEntity<GetFolloweeListResponseDto> success(List<FollowEntity> followEntities) {
        GetFolloweeListResponseDto responseBody = new GetFolloweeListResponseDto(followEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}