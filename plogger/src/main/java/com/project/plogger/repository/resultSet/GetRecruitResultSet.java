package com.project.plogger.repository.resultset;

public interface GetRecruitResultSet {
    
    Integer getRecruitPostId();
    String getRecruitPostTitle();
    String getRecruitPostContent();
    String getRecruitPostImage();
    String getRecruitPostWriter();
    String getRecruitPostCreatedAt();
    String getRecruitLocation();
    Integer getMinPeople();
    Integer getCurrentPeople();
    Integer getRecruitView();
    Integer getRecruitPostLike();
    Integer getRecruitReport();
    Integer getIsCompleted();

}
