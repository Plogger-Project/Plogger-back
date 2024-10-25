package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.ActiveCommentEntity;

import lombok.Getter;

@Getter
public class ActiveComment {

    private Integer activeCommentId;
    private String activeCommentWriter;
    private String activeCommentCreatedAt;
    private String activeCommentContent;

    private ActiveComment(ActiveCommentEntity activeCommentEntity) {
        this.activeCommentId = activeCommentEntity.getActiveCommentId();
        this.activeCommentWriter = activeCommentEntity.getActiveCommentWriter();
        this.activeCommentCreatedAt = activeCommentEntity.getActiveCommentCreatedAt();
        this.activeCommentContent = activeCommentEntity.getActiveCommentContent();
    }

    public static List<ActiveComment> getActiveCommentList(List<ActiveCommentEntity> activeCommentEntities) {

        List<ActiveComment> activeComments = new ArrayList<>();
        for(ActiveCommentEntity activeCommentEntity: activeCommentEntities) {
            ActiveComment activeComment = new ActiveComment(activeCommentEntity);
            activeComments.add(activeComment);
        }

        return activeComments;

    }
    
}
