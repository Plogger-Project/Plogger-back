package com.project.plogger.dto.response.active;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.ActiveComment;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.ActiveCommentEntity;

import lombok.Getter;

@Getter
public class GetActiveCommentListResponseDto extends ResponseDto{

    private List<ActiveComment> activeComments;

    private GetActiveCommentListResponseDto(List<ActiveCommentEntity> activeCommentEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.activeComments = ActiveComment.getActiveCommentList(activeCommentEntities);
    }

    public static ResponseEntity<GetActiveCommentListResponseDto> success(List<ActiveCommentEntity> activeCommentEntities) {
        GetActiveCommentListResponseDto responseBody = new GetActiveCommentListResponseDto(activeCommentEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
