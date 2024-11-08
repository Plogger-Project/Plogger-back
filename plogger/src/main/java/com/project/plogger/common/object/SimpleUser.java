package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.UserEntity;

import lombok.Getter;

@Getter
public class SimpleUser {
    private String userId;
    private String profileImage;

    public SimpleUser(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.profileImage = userEntity.getProfileImage();
    }

    public static List<SimpleUser> getList(List<UserEntity> userEntities) {
        List<SimpleUser> list = new ArrayList<>();
        
        for (UserEntity userEntity : userEntities) {
            SimpleUser simpleUser = new SimpleUser(userEntity);
            list.add(simpleUser);
        }

        return list;
    }
}
