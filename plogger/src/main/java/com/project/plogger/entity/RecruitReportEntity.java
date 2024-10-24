package com.project.plogger.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
    @GeneratedValue
    private Integer report_id;

    private String content;
    private String created_at;
    private Integer recruit_id;
    private String user_id;
}
