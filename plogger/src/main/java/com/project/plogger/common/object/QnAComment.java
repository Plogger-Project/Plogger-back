package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.QnACommentEntity;

import lombok.Getter;

@Getter
public class QnAComment {

    private Integer qnaCommentId;
    private String qnaCommentWriter;
    private String qnaCommentCreatedAt;
    private String qnaCommentContent;
    
    private QnAComment(QnACommentEntity qnaCommentEntity) {
        this.qnaCommentId = qnaCommentEntity.getQnaCommentId();
        this.qnaCommentWriter = qnaCommentEntity.getQnaCommentWriter();
        this.qnaCommentCreatedAt = qnaCommentEntity.getQnaCommentCreatedAt();
        this.qnaCommentContent = qnaCommentEntity.getQnaCommentContent();
    }

    public static List<QnAComment> getQnACommentList(List<QnACommentEntity> qnaCommentEntities) {

        List<QnAComment> qnaComments = new ArrayList<>();
        for(QnACommentEntity qnaCommentEntity: qnaCommentEntities) {
            QnAComment qnaComment = new QnAComment(qnaCommentEntity);
            qnaComments.add(qnaComment);
        }

        return qnaComments;

    }
}
