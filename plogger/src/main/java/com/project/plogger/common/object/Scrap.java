package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.RecruitScrapEntity;

import lombok.Getter;

@Getter
public class Scrap {
    private String userId;
    private Integer recruitId;
    private String createAt;

    public Scrap(RecruitScrapEntity recruitScrapEntity) {
        this.userId = recruitScrapEntity.getUserId();
        this.recruitId = recruitScrapEntity.getRecruitId();
        this.createAt = recruitScrapEntity.getCreatedAt();
    }

    public static List<Scrap> getList(List<RecruitScrapEntity> recruitScrapEntities) {
        
        List<Scrap> scraps = new ArrayList<>();
        for (RecruitScrapEntity recruitScrapEntity : recruitScrapEntities) {
            Scrap scrap = new Scrap(recruitScrapEntity);
            scraps.add(scrap);
        }

        return scraps;
    }
}
