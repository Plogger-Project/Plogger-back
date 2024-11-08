package com.project.plogger.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.plogger.dto.MileageDownDto;
import com.project.plogger.dto.MileageTagRemoveDto;
import com.project.plogger.dto.MileageUpDto;

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
@Table(name = "usermileage")
@Entity(name = "usermileage")
public class MileageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mileageId;
    private String userId;
    private Integer mileageChange;
    private String description;
    private String createdAt;
    private Integer mileageResult = 0;
    private Integer activeId;
    private Integer gifticonId;

    public MileageEntity(MileageUpDto dto) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.mileageChange = 150;
        this.description = "활동 게시판 작성";
        this.createdAt = simpleDateFormat.format(now);
        this.userId = dto.getUserId();
        this.activeId = dto.getActiveId();
        this.mileageResult = dto.getMileageResult();
    }

    public MileageEntity(MileageDownDto dto, Integer mileageChange) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.mileageChange = mileageChange;
        this.description = "기프티콘 구매";
        this.createdAt = simpleDateFormat.format(now);
        this.userId = dto.getUserId();
        this.gifticonId = dto.getGifticonId();
        this.mileageResult = dto.getMileageResult();
    }

    public MileageEntity(MileageTagRemoveDto dto) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.mileageChange = 150;
        this.description = "활동 게시판 태그 제거에 따른 마일리지 회수";
        this.createdAt = simpleDateFormat.format(now);
        this.userId = dto.getUserId();
        this.activeId = dto.getActiveId();
        this.mileageResult = dto.getMileageResult();
    }

}