package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.RecruitEntity;

import lombok.Getter;

@Getter
public class Recruit {
    private Integer recruitPostId;
    private String recruitPostTitle;
    private String recruitPostContent;
    private String recruitPostImage;
    private String recruitPostWriter;
    private String recruitPostCreatedAt;
    private String recruitLocation;
    private String recruitEndDate;
    private Integer minPeople;
    private Integer currentPeople;
    private Integer recruitView;
    private Integer recruitPostLike;
    private Integer recruitReport;
    private Boolean isCompleted;
    private Boolean isMileage;

    public Recruit(RecruitEntity recruitEntity) {
        this.recruitPostId = recruitEntity.getRecruitPostId();
        this.recruitPostTitle = recruitEntity.getRecruitPostTitle();
        this.recruitPostContent = recruitEntity.getRecruitPostContent();
        this.recruitPostImage = recruitEntity.getRecruitPostImage();
        this.recruitPostWriter = recruitEntity.getRecruitPostWriter();
        this.recruitPostCreatedAt = recruitEntity.getRecruitPostCreatedAt();
        this.recruitLocation = recruitEntity.getRecruitLocation();
        this.recruitEndDate = recruitEntity.getRecruitEndDate();
        this.minPeople = recruitEntity.getMinPeople();
        this.currentPeople = recruitEntity.getCurrentPeople();
        this.recruitView = recruitEntity.getRecruitView();
        this.recruitPostLike = recruitEntity.getRecruitPostLike();
        this.recruitReport = recruitEntity.getRecruitReport();
        this.isCompleted = recruitEntity.getIsCompleted();
        this.isMileage = recruitEntity.getIsMileage();
    }

    public static List<Recruit> getList(List<RecruitEntity> recruitEntities) {
        
        List<Recruit> recruits = new ArrayList<>();
        for (RecruitEntity recruitEntity : recruitEntities) {
            Recruit recruit = new Recruit(recruitEntity);
            recruits.add(recruit);
        }

        return recruits;
    }
}
