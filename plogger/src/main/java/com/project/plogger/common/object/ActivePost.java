package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.ActivePostEntity;

import lombok.Getter;

@Getter
public class ActivePost {

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

    public ActivePost(ActivePostEntity activePostEntity) {
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

    public static List<ActivePost> getList(List<ActivePostEntity> activePostEntities) {

        List<ActivePost> activePosts = new ArrayList<>();
        for (ActivePostEntity activePostEntity : activePostEntities) {
            ActivePost activePost = new ActivePost(activePostEntity);
            activePosts.add(activePost);
        }

        return activePosts;
    }

}
