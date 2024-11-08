package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.RecruitLikeEntity;

import lombok.Getter;

@Getter
public class RecruitLike {
    private String userId;
    private Integer recruitId;
    private String createAt;

    public RecruitLike(RecruitLikeEntity recruitLikeEntity) {
        this.userId = recruitLikeEntity.getUserId();
        this.recruitId = recruitLikeEntity.getRecruitId();
        this.createAt = recruitLikeEntity.getCreatedAt();
    }

    public static List<RecruitLike> getList(List<RecruitLikeEntity> recruitLikeEntities) {

        List<RecruitLike> recruitLikes = new ArrayList<>();
        for(RecruitLikeEntity recruitLikeEntity: recruitLikeEntities){
            RecruitLike recruitLike = new RecruitLike(recruitLikeEntity);
            recruitLikes.add(recruitLike);
        }

        return recruitLikes;

    }
}
