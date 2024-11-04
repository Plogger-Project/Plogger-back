package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.ActiveReportEntity;

import lombok.Getter;

@Getter
public class ActiveReport {

    private Integer reportId;
    private String content;
    private String createdAt;
    private Integer activeId;
    private String userId;

    public ActiveReport(ActiveReportEntity activeReportEntity) {
        this.reportId = activeReportEntity.getReportId();
        this.content = activeReportEntity.getContent();
        this.createdAt = activeReportEntity.getCreatedAt();
        this.activeId = activeReportEntity.getActiveId();
        this.userId = activeReportEntity.getUserId();
    }

    public static List<ActiveReport> getList(List<ActiveReportEntity> activeReportEntities) {

        List<ActiveReport> activeReports = new ArrayList<>();
        for (ActiveReportEntity activeReportEntity : activeReportEntities) {
            ActiveReport activeReport = new ActiveReport(activeReportEntity);
            activeReports.add(activeReport);
        }

        return activeReports;
    }
}
