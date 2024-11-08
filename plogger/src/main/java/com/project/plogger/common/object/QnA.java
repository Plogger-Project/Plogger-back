package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.QnAEntity;

import lombok.Getter;

@Getter
public class QnA {

    private Integer qnaPostId;
    private String qnaPostTitle;
    private String qnaPostWriter;
    private String qnaPostImage;
    private String qnaPostCreatedAt;
    private Boolean isPinned;

    private QnA(QnAEntity qnaEntity) {
        this.qnaPostId = qnaEntity.getQnaPostId();
        this.qnaPostTitle = qnaEntity.getQnaPostTitle();
        this.qnaPostWriter = qnaEntity.getQnaPostWriter();
        this.qnaPostImage = qnaEntity.getQnaPostImage();
        this.qnaPostCreatedAt = qnaEntity.getQnaPostCreatedAt();
        this.isPinned = qnaEntity.getIsPinned();
    }

    public static List<QnA> getList(List<QnAEntity> qnaEntities) {

        List<QnA> qnas = new ArrayList<>();
        for(QnAEntity qnaEntity: qnaEntities) {
            QnA qna = new QnA(qnaEntity);
            qnas.add(qna);
        }

        return qnas;

    }
}
