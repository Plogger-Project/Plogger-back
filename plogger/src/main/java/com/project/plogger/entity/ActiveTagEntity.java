package com.project.plogger.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.plogger.entity.pk.ActiveTagPk;

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
@Table(name = "active_tag")
@Entity(name = "active_tag")
@IdClass(ActiveTagPk.class)
public class ActiveTagEntity {
    
    @Id
    private String userId;
    @Id
    private Integer activeId;
    private Integer recruitId;
    private String createdAt;

    public ActiveTagEntity(String userId, Integer activeId, Integer recruitId) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.userId = userId;
        this.activeId = activeId;
        this.recruitId = recruitId;
        this.createdAt = simpleDateFormat.format(now);
    }

}