package com.project.plogger.dto.response.recruit;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.repository.resultset.GetRecruitResultSet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseCode;

import lombok.Getter;

@Getter
public class GetRecruitResponseDto  extends ResponseDto{
    
    private Integer recruitPostId;
    private String recruitPostTitle;
    private String recruitPostContent;
    private String recruitPostImage;
    private String recruitPostWriter;
    private String recruitPostCreatedAt;
    private String recruitEndDate;
    private String recruitLocation;
    private Integer minPeople;
    private Integer currentPeople;
    private Integer recruitView;
    private Integer recruitPostLike;
    private Integer recruitReport;
    private Integer isCompleted;

    public GetRecruitResponseDto(GetRecruitResultSet resultSet) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.recruitPostId = resultSet.getRecruitPostId();
        this.recruitPostTitle = resultSet.getRecruitPostTitle();
        this.recruitPostContent = resultSet.getRecruitPostContent();
        this.recruitPostImage = resultSet.getRecruitPostImage();
        this.recruitPostWriter = resultSet.getRecruitPostWriter();
        this.recruitPostCreatedAt = resultSet.getRecruitPostCreatedAt();
        this.recruitEndDate = resultSet.getRecruitEndDate();
        this.recruitLocation = resultSet.getRecruitLocation();
        this.minPeople = resultSet.getMinPeople();
        this.currentPeople = resultSet.getCurrentPeople();
        this.recruitView = resultSet.getRecruitView();
        this.recruitPostLike = resultSet.getRecruitPostLike();
        this.recruitReport = resultSet.getRecruitReport();
        this.isCompleted = resultSet.getIsCompleted();

    }
    
    public static ResponseEntity<GetRecruitResponseDto> success(GetRecruitResultSet resultSet) {
        GetRecruitResponseDto responseBody = new GetRecruitResponseDto(resultSet);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
