package com.project.plogger.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.plogger.entity.pk.RecruitScrapPk;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "recruit_scrap")
@Table(name = "recruit_scrap")
@IdClass(RecruitScrapPk.class)
public class RecruitScrapEntity {
    
    @Id
    private String userId;
    @Id
    private Integer recruitId;
    private String createdAt;

    public RecruitScrapEntity(String userId, Integer recruitId) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.userId = userId;
        this.recruitId = recruitId;
        this.createdAt = simpleDateFormat.format(now);
    }
    
}
