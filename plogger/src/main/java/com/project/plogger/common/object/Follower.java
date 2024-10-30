package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.FollowEntity;

import lombok.Getter;

@Getter
public class Follower {

    private Integer followId;
    private String followerId;
    private String followeeId;

    private Follower(FollowEntity followEntity) {
        this.followId = followEntity.getFollowId();
        this.followerId = followEntity.getFollowerId();
        this.followeeId = followEntity.getFolloweeId();
    }

    public static List<Follower> getList(List<FollowEntity> followEntities) {

        List<Follower> follows = new ArrayList<>();
        for(FollowEntity followEntity: followEntities) {
            Follower follow = new Follower(followEntity);
            follows.add(follow);
        }

        return follows;

    }
    
}
