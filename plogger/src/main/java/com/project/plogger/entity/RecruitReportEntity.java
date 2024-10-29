package com.project.plogger.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.plogger.dto.request.recruit.RecruitReportRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recruitreport")
@Entity(name = "recruitreport")
public class RecruitReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;
    private String content;
    private String createdAt;
    private Integer recruitId;
    private String userId;

    public RecruitReportEntity(RecruitReportRequestDto dto, Integer recruitId, String userId) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.content = dto.getContent();
        this.createdAt = simpleDateFormat.format(now);
        this.recruitId = recruitId;
        this.userId = userId;
    }
}
