package com.project.plogger.dto.response.active;

public interface GetActivePostResultSet {
    Integer getActivePostId();
    String getActivePostTitle();
    String getActivePostWriterId();
    String getActivePostImage();
    String getActivePostContent();
    String getActiveLocation();
    String getActivePostCraetedAt();
    String getActiveStartDate();
    String getActiveEndDate();
    Integer getActiveView();
    Integer getActivePostLike();
    Integer getActiveReport();
}
