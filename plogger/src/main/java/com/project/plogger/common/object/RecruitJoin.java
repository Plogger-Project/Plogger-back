package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.RecruitJoinEntity;

import lombok.Getter;

@Getter
public class RecruitJoin {
    private String userId;
    

    public RecruitJoin(RecruitJoinEntity recruitJoinEntity) {
        this.userId = recruitJoinEntity.getUserId();
    }

    public static List<RecruitJoin> getJoinList(List<RecruitJoinEntity> recruitJoinEntities) {
        
        List<RecruitJoin> joins = new ArrayList<>();
        for (RecruitJoinEntity recruitEntity : recruitJoinEntities) {
            RecruitJoin recruitJoin = new RecruitJoin(recruitEntity);
            joins.add(recruitJoin);
        }

        return joins;
    }
}
