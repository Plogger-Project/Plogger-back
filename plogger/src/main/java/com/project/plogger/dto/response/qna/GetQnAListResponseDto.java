package com.project.plogger.dto.response.qna;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.QnA;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.QnAEntity;

import lombok.Getter;

@Getter
public class GetQnAListResponseDto extends ResponseDto{

    private List<QnA> qnaPosts;

    private GetQnAListResponseDto(List<QnAEntity> qnaEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.qnaPosts = QnA.getList(qnaEntities);
    }

    public static ResponseEntity<GetQnAListResponseDto> success(List<QnAEntity> qnaEntities) {
        GetQnAListResponseDto responseBody = new GetQnAListResponseDto(qnaEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
