package com.project.plogger.dto.response.recruit;

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
public class GetRecruitListResponseDto extends ResponseDto{
    
    private List<Recruit> recruitPosts;

    private GetRecruitListResponseDto(List<RecruitEntity> recruitEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.recruitPosts = Recruit.getList(recruitEntities);
    }

    public static ResponseEntity<GetRecruitListResponseDto> success(List<RecruitEntity> recruitEntities) {
        GetRecruitListResponseDto responseBody = new GetRecruitListResponseDto(recruitEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
