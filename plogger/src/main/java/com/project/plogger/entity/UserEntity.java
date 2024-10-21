package com.project.plogger.entity;

import com.project.plogger.dto.request.auth.SignUpRequestDto;
import com.project.plogger.dto.request.user.PatchUserRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
@Table(name = "user")
public class UserEntity {

    @Id
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

    public UserEntity(SignUpRequestDto dto) {
        this.userId = dto.getUserId();
        this.name = dto.getName();
        this.password = dto.getPassword();
        this.telNumber = dto.getTelNumber();
        this.address = dto.getAddress();
        this.profileImage = dto.getProfileImage();
        this.ecoScore = dto.getEcoScore();
        this.mileage = dto.getMileage();
        this.comment = dto.getComment();
        this.joinPath = dto.getJoinPath();
        this.snsId = dto.getSnsId();
        this.isAdmin = dto.getIsAdmin();
    }

    public void patch(PatchUserRequestDto dto) {
        this.profileImage = dto.getProfileImage();
        this.name = dto.getName();
        this.password = dto.getPassword();
        this.telNumber = dto.getTelNumber();
        this.address = dto.getAddress();
    }

}
