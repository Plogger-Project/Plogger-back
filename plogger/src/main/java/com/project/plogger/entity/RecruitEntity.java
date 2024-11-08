package com.project.plogger.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.project.plogger.dto.request.recruit.PatchRecruitRequestDto;
import com.project.plogger.dto.request.recruit.PostRecruitRequestDto;

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
@Entity(name = "recruitpost")
@Table(name = "recruitpost")
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
    private String recruitAddress = "기본주소";
    private String recruitEndDate;
    private Integer minPeople;
    private Integer currentPeople=1;
    private Integer recruitView=0;
    private Integer recruitPostLike=0;
    private Integer recruitReport=0;
    private Boolean isCompleted = false;
    private Boolean isMileage = false;

    private static final DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void setRecruitPostCreatedAt() {
        this.recruitPostCreatedAt = LocalDateTime.now().format(Formatter);
    }

    public RecruitEntity(PostRecruitRequestDto dto) {

        this.recruitPostTitle = dto.getRecruitPostTitle();
        this.recruitPostContent = dto.getRecruitPostContent();
        this.recruitPostImage = dto.getRecruitPostImage();
        this.recruitLocation = dto.getRecruitLocation();
        this.recruitAddress = dto.getRecruitAddress();
        this.minPeople = dto.getMinPeople();
        this.recruitEndDate = dto.getRecruitEndDate();

    }
    
    public void RecruitPatch(PatchRecruitRequestDto dto) {
        this.recruitPostTitle = dto.getRecruitPostTitle();
        this.recruitPostContent = dto.getRecruitPostContent();
        this.recruitPostImage = dto.getRecruitPostImage();
        this.minPeople = dto.getMinPeople();
        this.recruitEndDate = dto.getRecruitEndDate();
    }

}
