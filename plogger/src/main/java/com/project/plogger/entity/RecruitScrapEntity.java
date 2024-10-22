package com.project.plogger.entity;

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
@Entity(name = "scrap")
@Table(name = "scrap")
@IdClass(RecruitScrapPk.class)
public class RecruitScrapEntity {
    
    @Id
    private String userId;
    @Id
    private String recruitId;
    private String createdAt;
    
}
