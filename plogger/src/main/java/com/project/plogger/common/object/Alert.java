package com.project.plogger.common.object;

import com.project.plogger.entity.alert.AlertEntity;
import java.util.List;
import java.util.ArrayList;

import lombok.Getter;

@Getter
public class Alert {

    private Long id;
    private String userId;
    private String message;
    private Boolean isRead;
    private String createdAt;

    public Alert(AlertEntity alertEntity) {
        this.id = alertEntity.getId();
        this.userId = alertEntity.getUserId();
        this.message = alertEntity.getMessage();
        this.isRead = alertEntity.isRead();
        this.createdAt = alertEntity.getCreatedAt();
    }

    public static List<Alert> getList(List<AlertEntity> alertEntities) {

        List<Alert> alerts = new ArrayList<>();
        for (AlertEntity alertEntity : alertEntities) {
            Alert alert = new Alert(alertEntity);
            alerts.add(alert);
        }

        return alerts;
    }
}
