package com.project.plogger.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.plogger.entity.pk.RecruitLikePk;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "recruit_like")
@Entity(name = "recruit_like")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(RecruitLikePk.class)
public class RecruitLikeEntity {

    @Id
    private String userId;
    @Id
    private Integer recruitId;
    private String createdAt;

    public RecruitLikeEntity(String userId, Integer recruitId) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.userId = userId;
        this.recruitId = recruitId;
        this.createdAt = simpleDateFormat.format(now);
    } 

}
