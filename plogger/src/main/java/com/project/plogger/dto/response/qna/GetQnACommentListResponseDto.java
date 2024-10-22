package com.project.plogger.dto.response.qna;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.QnAComment;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.QnACommentEntity;

import lombok.Getter;

@Getter
public class GetQnACommentListResponseDto extends ResponseDto{

    private List<QnAComment> qnaComments;


    private GetQnACommentListResponseDto(List<QnACommentEntity> qnaCommentEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.qnaComments = QnAComment.getQnACommentList(qnaCommentEntities);
    } 

    public static ResponseEntity<GetQnACommentListResponseDto> success(List<QnACommentEntity> qnaCommentEntities) {
        GetQnACommentListResponseDto responseBody = new GetQnACommentListResponseDto(qnaCommentEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
