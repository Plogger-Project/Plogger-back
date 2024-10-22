package com.project.plogger.dto.response.qna;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.QnAEntity;

import lombok.Getter;

@Getter
public class GetQnAResponseDto extends ResponseDto{

    private Integer qnaPostId;
    private String qnaPostTitle;
    private String qnaPostContent;
    private String qnaPostImage;
    private String qnaPostWriter;
    private String qnaPostCreatedAt;
    private Boolean isPinned;

    private GetQnAResponseDto(QnAEntity qnaEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.qnaPostId = qnaEntity.getQnaPostId();
        this.qnaPostTitle = qnaEntity.getQnaPostTitle();
        this.qnaPostContent = qnaEntity.getQnaPostContent();
        this.qnaPostImage = qnaEntity.getQnaPostImage();
        this.qnaPostWriter = qnaEntity.getQnaPostWriter();
        this.qnaPostCreatedAt = qnaEntity.getQnaPostCreatedAt();
        this.isPinned = qnaEntity.getIsPinned();
    }

    public static ResponseEntity<GetQnAResponseDto> success(QnAEntity qnaEntity) {
        GetQnAResponseDto responseBody = new GetQnAResponseDto(qnaEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
