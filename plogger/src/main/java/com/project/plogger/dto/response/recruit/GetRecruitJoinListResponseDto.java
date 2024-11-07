package com.project.plogger.dto.response.recruit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.RecruitJoinEntity;

import lombok.Getter;

@Getter
public class GetRecruitJoinListResponseDto extends ResponseDto {
    
    private List<String> joins;

    private GetRecruitJoinListResponseDto(List<RecruitJoinEntity> recruitJoinEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        List<String> joins = new ArrayList<>();
        for (RecruitJoinEntity recruitJoinEntity: recruitJoinEntities) joins.add(recruitJoinEntity.getUserId());
        this.joins = joins;
    }

    public static ResponseEntity<GetRecruitJoinListResponseDto> success(List<RecruitJoinEntity> recruitJoinEntities) {
        GetRecruitJoinListResponseDto responseBody = new GetRecruitJoinListResponseDto(recruitJoinEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
