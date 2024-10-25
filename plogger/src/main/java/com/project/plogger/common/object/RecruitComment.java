package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.RecruitCommentEntity;

import lombok.Getter;

@Getter
public class RecruitComment {

    private Integer recruitCommentId;
    private String recruitCommentWriter;
    private String recruitCommentCreatedAt;
    private String recruitCommentContent;

    private RecruitComment(RecruitCommentEntity recruitCommentEntity) {
        this.recruitCommentId = recruitCommentEntity.getRecruitCommentId();
        this.recruitCommentWriter = recruitCommentEntity.getRecruitCommentWriter();
        this.recruitCommentCreatedAt = recruitCommentEntity.getRecruitCommentCreatedAt();
        this.recruitCommentContent = recruitCommentEntity.getRecruitCommentContent();
    }

    public static List<RecruitComment> getRecruitCommentList(List<RecruitCommentEntity> recruitCommentEntities) {

        List<RecruitComment> recruitComments = new ArrayList<>();
        for(RecruitCommentEntity recruitCommentEntity: recruitCommentEntities) {
            RecruitComment recruitComment = new RecruitComment(recruitCommentEntity);
            recruitComments.add(recruitComment);
        }

        return recruitComments;

    }
    
}
