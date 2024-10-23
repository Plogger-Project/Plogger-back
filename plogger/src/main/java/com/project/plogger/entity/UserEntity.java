package com.project.plogger.entity;

import com.project.plogger.dto.request.auth.SignUpRequestDto;
import com.project.plogger.dto.request.user.PatchUserRequestDto;

import jakarta.persistence.Column;
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
    @Column(columnDefinition = "varchar(255) default 'https://blog.kakaocdn.net/dn/4CElL/btrQw18lZMc/Q0oOxqQNdL6kZp0iSKLbV1/img.png'")
    private String profileImage = "https://blog.kakaocdn.net/dn/4CElL/btrQw18lZMc/Q0oOxqQNdL6kZp0iSKLbV1/img.png";
    @Column(columnDefinition = "int default 0")
    private Integer ecoScore = 0;
    @Column(columnDefinition = "int default 0")
    private Integer mileage = 0;
    private String comment;
    @Column(columnDefinition = "text default 'home'")
    private String joinPath = "home";
    private String snsId;
    @Column(columnDefinition = "boolean default false")
    private Boolean isAdmin = false;

    public UserEntity(SignUpRequestDto dto) {
        this.userId = dto.getUserId();
        this.name = dto.getName();
        this.password = dto.getPassword();
        this.telNumber = dto.getTelNumber();
        this.address = dto.getAddress();
        this.joinPath = dto.getJoinPath();
        this.snsId = dto.getSnsId();
    }

    public void patch(PatchUserRequestDto dto) {
        this.profileImage = dto.getProfileImage();
        this.name = dto.getName();
        this.password = dto.getPassword();
        this.telNumber = dto.getTelNumber();
        this.address = dto.getAddress();
    }

}
