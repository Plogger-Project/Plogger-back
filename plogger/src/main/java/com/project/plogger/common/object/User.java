package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.UserEntity;

import lombok.Getter;

@Getter
public class User {
    private String userId;
    private String name;
    private String telNumber;

    private User(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.name = userEntity.getName();
        this.telNumber = userEntity.getTelNumber();
    }

    public static List<User> getList(List<UserEntity> userEntities) {
        
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            User user = new User(userEntity);
            users.add(user);
        }
        return users;
    }
}
