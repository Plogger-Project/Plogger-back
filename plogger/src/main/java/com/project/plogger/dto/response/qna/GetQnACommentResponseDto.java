package com.project.plogger.dto.response.qna;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.QnACommentEntity;

import lombok.Getter;

@Getter
public class GetQnACommentResponseDto extends ResponseDto{

    private Integer qnaCommentId;
    private Integer qnaId;
    private String qnaCommentWriter;
    private String qnaCommentCreatedAt;
    private String qnaCommentContent;

    private GetQnACommentResponseDto(QnACommentEntity qnaCommentEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.qnaCommentId = qnaCommentEntity.getQnaCommentId();
        this.qnaId = qnaCommentEntity.getQnaId();
        this.qnaCommentWriter = qnaCommentEntity.getQnaCommentWriter();
        this.qnaCommentCreatedAt = qnaCommentEntity.getQnaCommentCreatedAt();
        this.qnaCommentContent = qnaCommentEntity.getQnaCommentContent();
    }

    public static ResponseEntity<GetQnACommentResponseDto> success(QnACommentEntity qnaEntity) {
        GetQnACommentResponseDto responseBody = new GetQnACommentResponseDto(qnaEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
