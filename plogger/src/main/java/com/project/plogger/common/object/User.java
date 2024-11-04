package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.UserEntity;

import lombok.Getter;

@Getter
public class User {
    private String userId;
    private String name;
    private String password;
    private String telNumber;
    private String address;
    private String profileImage;
    private Integer ecoScore;
    private Integer mileage;
    private String comment;
    private String joinPath;
    private String snsId;
    private Boolean isAdmin;

    public User(UserEntity userEntity) {
        this.userId = userEntity.getUserId();
        this.name = userEntity.getName();
        this.password = userEntity.getPassword();
        this.telNumber = userEntity.getTelNumber();
        this.address = userEntity.getAddress();
        this.profileImage = userEntity.getProfileImage();
        this.ecoScore = userEntity.getEcoScore();
        this.mileage = userEntity.getMileage();
        this.comment = userEntity.getComment();
        this.joinPath = userEntity.getJoinPath();
        this.snsId = userEntity.getSnsId();
        this.isAdmin = userEntity.getIsAdmin();
    }

    public static List<User> getList(List<UserEntity> userEntities) {
        List<User> users = new ArrayList<>();
        for (UserEntity userEntity: userEntities) {
            User user = new User(userEntity);
            users.add(user);
        }

        return users;
    }

}
