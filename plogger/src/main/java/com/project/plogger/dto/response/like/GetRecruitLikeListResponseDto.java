package com.project.plogger.dto.response.like;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.RecruitLike;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.RecruitLikeEntity;

import lombok.Getter;

@Getter
public class GetRecruitLikeListResponseDto extends ResponseDto{

    private List<RecruitLike> recruitLikes;

    private GetRecruitLikeListResponseDto(List<RecruitLikeEntity> recruitLikeEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.recruitLikes = RecruitLike.getList(recruitLikeEntities);
    }

    public static ResponseEntity<GetRecruitLikeListResponseDto> success(List<RecruitLikeEntity> recruitLikeEntities) {
        GetRecruitLikeListResponseDto responseBody = new GetRecruitLikeListResponseDto(recruitLikeEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
