package com.project.plogger.dto.response.follow;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.Follower;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.FollowEntity;

import lombok.Getter;

@Getter
public class GetFollowerListResponseDto extends ResponseDto {

    private List<Follower> follows;

    public GetFollowerListResponseDto(List<FollowEntity> followEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.follows = Follower.getList(followEntities);
    }

    public static ResponseEntity<GetFollowerListResponseDto> success(List<FollowEntity> followEntities) {
        GetFollowerListResponseDto responseBody = new GetFollowerListResponseDto(followEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
