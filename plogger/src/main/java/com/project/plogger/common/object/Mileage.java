package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.MileageEntity;

import lombok.Getter;

@Getter
public class Mileage {

    private Integer mileageId;
    private String userId;
    private Integer mileageChange;
    private String description;
    private String createdAt;
    private Integer mileageResult;
    private Integer activeId;
    private Integer gifticonId;

    private Mileage(MileageEntity mileageEntity) {
        this.mileageId = mileageEntity.getMileageId();
        this.userId = mileageEntity.getUserId();
        this.mileageChange = mileageEntity.getMileageChange();
        this.description = mileageEntity.getDescription();
        this.createdAt = mileageEntity.getCreatedAt();
        this.mileageResult = mileageEntity.getMileageResult();
        this.activeId = mileageEntity.getActiveId();
        this.gifticonId = mileageEntity.getGifticonId();
    }

    public static List<Mileage> getList(List<MileageEntity> mileageEntities) {

        List<Mileage> mileages = new ArrayList<>();
        for(MileageEntity mileageEntity: mileageEntities) {
            Mileage mileage = new Mileage(mileageEntity);
            mileages.add(mileage);
        }

        return mileages;

    } 
    
}
