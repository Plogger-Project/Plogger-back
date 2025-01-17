package com.project.plogger.dto.response.active;

import java.util.List;

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
    private String activeAddress;
    private String activePostCreatedAt;
    private String activeStartDate;
    private String activeEndDate;
    private Integer activeView;
    private Integer activePostLike;
    private Integer activeReport;
    private Integer recruitId;
    private List<String> activePeople;
    
    private GetActivePostResponseDto(ActivePostEntity activePostEntity, List<String> activePeople) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.activePostId = activePostEntity.getActivePostId();
        this.activePostTitle = activePostEntity.getActivePostTitle();
        this.activePostWriterId = activePostEntity.getActivePostWriterId();
        this.activePostImage = activePostEntity.getActivePostImage();
        this.activePostContent = activePostEntity.getActivePostContent();
        this.activeLocation = activePostEntity.getActiveLocation();
        this.activeAddress = activePostEntity.getActiveAddress();
        this.activePostCreatedAt = activePostEntity.getActivePostCreatedAt();
        this.activeStartDate = activePostEntity.getActiveStartDate();
        this.activeEndDate = activePostEntity.getActiveEndDate();
        this.activeView = activePostEntity.getActiveView();
        this.activePostLike = activePostEntity.getActivePostLike();
        this.activeReport = activePostEntity.getActiveReport();
        this.recruitId = activePostEntity.getRecruitId();
        this.activePeople = activePeople;
    }

    public static ResponseEntity<GetActivePostResponseDto> success(ActivePostEntity activePostEntity, List<String> activePeople) {
        GetActivePostResponseDto responseBody = new GetActivePostResponseDto(activePostEntity, activePeople);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        
    }

}
