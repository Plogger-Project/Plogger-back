package com.project.plogger.dto.response.like;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.RecruitLikeEntity;

import lombok.Getter;

@Getter
public class GetRecruitLikeResponseDto extends ResponseDto {

    private List<String> userIds;

    private GetRecruitLikeResponseDto(List<RecruitLikeEntity> recruitLikeEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        List<String> userIds = new ArrayList<>();
        for (RecruitLikeEntity recruitLikeEntity: recruitLikeEntities) userIds.add(recruitLikeEntity.getUserId());
        this.userIds = userIds;
    }

    public static ResponseEntity<GetRecruitLikeResponseDto> success(List<RecruitLikeEntity> recruitLikeEntities) {
        GetRecruitLikeResponseDto responseBody = new GetRecruitLikeResponseDto(recruitLikeEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
