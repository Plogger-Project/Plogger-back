package com.project.plogger.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.plogger.dto.request.active.ActiveReportRequestDto;

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
@Table(name = "activereport")
@Entity(name = "activereport")
public class ActiveReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;


    private String content;

    private String createdAt;

    private Integer activeId;

    private String userId;

    public ActiveReportEntity(ActiveReportRequestDto dto, Integer activeId, String userId) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.content = dto.getContent();
        this.createdAt = simpleDateFormat.format(now);
        this.activeId = activeId;
        this.userId = userId;
    }
}
