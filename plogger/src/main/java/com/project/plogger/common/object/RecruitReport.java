package com.project.plogger.common.object;

import com.project.plogger.entity.RecruitReportEntity;

import java.util.List;
import java.util.ArrayList;
import lombok.Getter;

@Getter
public class RecruitReport {

    private Integer reportId;
    private String content;
    private String createdAt;
    private Integer recruitId;
    private String userId;

    public RecruitReport(RecruitReportEntity recruitReportEntity) {
        this.reportId = recruitReportEntity.getReportId();
        this.content = recruitReportEntity.getContent();
        this.createdAt = recruitReportEntity.getCreatedAt();
        this.recruitId = recruitReportEntity.getRecruitId();
        this.userId = recruitReportEntity.getUserId();
    }

    public static List<RecruitReport> getList(List<RecruitReportEntity> recruitReportEntities) {

        List<RecruitReport> recruitReports = new ArrayList<>();
        for (RecruitReportEntity recruitReportEntity : recruitReportEntities) {
            RecruitReport recruitReport = new RecruitReport(recruitReportEntity);
            recruitReports.add(recruitReport);
        }

        return recruitReports;
    }
}
