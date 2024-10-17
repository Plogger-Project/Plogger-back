package com.project.plogger.entity;

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
public class User {

    @Id
    private Integer userId;
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

}
