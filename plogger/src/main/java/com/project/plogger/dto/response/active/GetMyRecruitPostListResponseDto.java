package com.project.plogger.dto.response.active;

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
public class GetMyRecruitPostListResponseDto extends ResponseDto {
    
    private List<Recruit> myRecruitPosts;
    
    public GetMyRecruitPostListResponseDto(List<RecruitEntity> recruitEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.myRecruitPosts = Recruit.getList(recruitEntities);
    }

    public static ResponseEntity<GetMyRecruitPostListResponseDto> success(List<RecruitEntity> recruitEntities) {
        GetMyRecruitPostListResponseDto responseBody = new GetMyRecruitPostListResponseDto(recruitEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }


}
