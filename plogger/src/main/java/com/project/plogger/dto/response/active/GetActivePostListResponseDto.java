package com.project.plogger.dto.response.active;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.ActivePost;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.ActivePostEntity;

import lombok.Getter;

@Getter
public class GetActivePostListResponseDto extends ResponseDto {

    private List<ActivePost> activePosts;

    public GetActivePostListResponseDto(List<ActivePostEntity> activePostEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.activePosts = ActivePost.getList(activePostEntities);
    }

    public static ResponseEntity<GetActivePostListResponseDto> success(List<ActivePostEntity> activePostEntities) {
        GetActivePostListResponseDto responseBody = new GetActivePostListResponseDto(activePostEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
