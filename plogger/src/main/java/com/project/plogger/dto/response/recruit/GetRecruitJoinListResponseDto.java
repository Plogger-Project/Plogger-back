package com.project.plogger.dto.response.recruit;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.SimpleUser;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.UserEntity;

import lombok.Getter;

@Getter
public class GetRecruitJoinListResponseDto extends ResponseDto {
    
    private List<SimpleUser> joins;

    private GetRecruitJoinListResponseDto(List<UserEntity> userEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.joins = SimpleUser.getList(userEntities);
    }

    public static ResponseEntity<GetRecruitJoinListResponseDto> success(List<UserEntity> userEntities) {
        GetRecruitJoinListResponseDto responseBody = new GetRecruitJoinListResponseDto(userEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
