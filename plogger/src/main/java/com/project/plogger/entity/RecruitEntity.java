package com.project.plogger.entity;

import com.project.plogger.dto.request.recruit.PostRecruitRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "recruit")
public class RecruitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recruitPostId;
    private String recruitPostTitle;
    private String recruitPostContent;
    private String recruitPostImage;
    private String recruitPostWriter;
    private String recruitPostCreatedAt;
    private String recruitLocation;
    private Integer minPeople;
    private Integer currentPeople;
    private Integer recruitView;
    private Integer recruitPostLike;
    private Integer recruitReport;
    private Integer isCompleted;
    
    public RecruitEntity(PostRecruitRequestDto dto){
        this.recruitPostTitle = dto.getRecruitPostTitle();
        this.recruitPostContent = dto.getRecruitPostContent();
        this.recruitPostImage = dto.getRecruitPostImage();
        this.recruitPostWriter = dto.getRecruitPostWriter();
        this.recruitPostCreatedAt = dto.getRecruitPostCreatedAt();
        this.recruitLocation = dto.getRecruitLocation();
        this.minPeople = dto.getMinPeople();
        this.currentPeople = dto.getCurrentPeople();
        this.recruitView = dto.getRecruitView();
        this.recruitPostLike = dto.getRecruitPostLike();
        this.recruitReport = dto.getRecruitReport();
        this.isCompleted = dto.getIsCompleted();
    }


    
}
