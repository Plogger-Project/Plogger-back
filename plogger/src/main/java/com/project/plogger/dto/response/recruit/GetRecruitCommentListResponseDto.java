package com.project.plogger.dto.response.recruit;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.RecruitComment;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.RecruitCommentEntity;

import lombok.Getter;

@Getter
public class GetRecruitCommentListResponseDto extends ResponseDto{

    private List<RecruitComment> recruitComments;

    private GetRecruitCommentListResponseDto(List<RecruitCommentEntity> recruitCommentEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.recruitComments = RecruitComment.getRecruitCommentList(recruitCommentEntities);
    }

    public static ResponseEntity<GetRecruitCommentListResponseDto> success(List<RecruitCommentEntity> recruitCommentEntities) {
        GetRecruitCommentListResponseDto responseBody = new GetRecruitCommentListResponseDto(recruitCommentEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    
}
