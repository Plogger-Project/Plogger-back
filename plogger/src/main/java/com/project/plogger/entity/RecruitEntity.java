package com.project.plogger.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.plogger.dto.request.recruit.PostRecruitRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "recruit")
@Table(name = "recruit")
public class RecruitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recruitPostId;
    @Size(max=100)
    private String recruitPostTitle;
    private String recruitPostContent;
    private String recruitPostImage;
    @Size(max = 20)
    private String recruitPostWriter;
    private String recruitPostCreatedAt;
    private String recruitLocation;
    private String recruitEndDate;
    private Integer minPeople;
    @Column(columnDefinition = "int default 1")
    private Integer currentPeople;
    @Column(columnDefinition = "int default 0")
    private Integer recruitView;
    @Column(columnDefinition = "int default 0")
    private Integer recruitPostLike;
    @Column(columnDefinition = "int default 0")
    private Integer recruitReport;
    @Column(columnDefinition = "boolean default false")
    private Integer isCompleted;
    
    public RecruitEntity(PostRecruitRequestDto dto) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.recruitPostTitle = dto.getRecruitPostTitle();
        this.recruitPostContent = dto.getRecruitPostContent();
        this.recruitPostImage = dto.getRecruitPostImage();
        this.recruitLocation = dto.getRecruitLocation();
        this.minPeople = dto.getMinPeople();
        this.recruitPostCreatedAt = simpleDateFormat.format(now);
    }


    
}
