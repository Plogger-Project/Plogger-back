package com.project.plogger.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.plogger.entity.pk.ActiveLikePk;

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
@Entity(name = "active_like")
@Table(name = "active_like")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ActiveLikePk.class)
public class ActiveLikeEntity {

    @Id
    private String userId;
    @Id
    private Integer activeId;
    private String createdAt;

    public ActiveLikeEntity(String userId, Integer activeId) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.userId = userId;
        this.activeId = activeId;
        this.createdAt = simpleDateFormat.format(now);
    } 
    
}
