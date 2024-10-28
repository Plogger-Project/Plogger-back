package com.project.plogger.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.plogger.dto.request.mileage.PostMileageDownRequestDto;
import com.project.plogger.dto.request.mileage.PostMileageUpRequestDto;

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
    private Integer mileageResult;
    private Integer activeId;
    private Integer gifticonId;

    public MileageEntity(PostMileageUpRequestDto dto) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.mileageChange = 150;
        this.description = "활동 게시판 작성";
        this.createdAt = simpleDateFormat.format(now);
    }

    public MileageEntity(PostMileageDownRequestDto dto) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.description = "기프티콘 구매";
        this.createdAt = simpleDateFormat.format(now);
    }
    
}
