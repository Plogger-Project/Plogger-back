package com.project.plogger.dto.response.active;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.ActivePostEntity;

import lombok.Getter;

@Getter
public class GetActivePostResponseDto extends ResponseDto {

    private Integer activePostId;
    private String activePostTitle;
    private String activePostWriterId;
    private String activePostImage;
    private String activePostContent;
    private String activeLocation;
    private String activePostCraetedAt;
    private String activeStartDate;
    private String activeEndDate;
    private Integer activeView;
    private Integer activePostLike;
    private Integer activeReport;
    
    private GetActivePostResponseDto(ActivePostEntity activePostEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.activePostId = activePostEntity.getActivePostId();
        this.activePostTitle = activePostEntity.getActivePostTitle();
        this.activePostWriterId = activePostEntity.getActivePostWriterId();
        this.activePostImage = activePostEntity.getActivePostImage();
        this.activePostContent = activePostEntity.getActivePostContent();
        this.activeLocation = activePostEntity.getActiveLocation();
        this.activePostCraetedAt = activePostEntity.getActivePostCreatedAt();
        this.activeStartDate = activePostEntity.getActiveStartDate();
        this.activeEndDate = activePostEntity.getActiveEndDate();
        this.activeView = activePostEntity.getActiveView();
        this.activePostLike = activePostEntity.getActivePostLike();
        this.activeReport = activePostEntity.getActiveReport();
    }

    public static ResponseEntity<GetActivePostResponseDto> success(ActivePostEntity activePostEntity) {
        GetActivePostResponseDto responseBody = new GetActivePostResponseDto(activePostEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        
    }

}
