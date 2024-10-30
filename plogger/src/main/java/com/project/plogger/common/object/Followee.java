package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.FollowEntity;

import lombok.Getter;

@Getter
public class Followee {

    private Integer followId;
    private String followerId;
    private String followeeId;

    private Followee(FollowEntity followEntity) {
        this.followId = followEntity.getFollowId();
        this.followerId = followEntity.getFollowerId();
        this.followeeId = followEntity.getFolloweeId();
    }

    public static List<Followee> getList(List<FollowEntity> followEntities) {

        List<Followee> follows = new ArrayList<>();
        for(FollowEntity followEntity: followEntities) {
            Followee follow = new Followee(followEntity);
            follows.add(follow);
        }

        return follows;

    }
    
}
