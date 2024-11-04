package com.project.plogger.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.project.plogger.dto.request.active.PatchActivePostRequestDto;
import com.project.plogger.dto.request.active.PostActivePostRequestDto;

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
@Table(name = "active_post")
@Entity(name = "active_post")
@NoArgsConstructor
@AllArgsConstructor
public class ActivePostEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activePostId;
    @Size(max = 100)
    private String activePostTitle;
    private String activePostContent;
    @Size(max = 20)
    private String activePostWriterId;
    private String activePostCreatedAt;
    private String activeLocation;
    private String activeStartDate;
    private String activeEndDate;
    private Integer activeView = 0;
    private Integer activePostLike = 0;
    private Integer activeReport = 0;
    private String activePostImage;
    private Integer recruitId;
    
    public ActivePostEntity(PostActivePostRequestDto dto) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.activePostTitle = dto.getActivePostTitle();
        this.activePostContent = dto.getActivePostContent();
        this.activePostImage = dto.getActivePostImage();
        this.activeStartDate = dto.getActiveStartDate();
        this.activeEndDate = dto.getActiveEndDate();
        this.activeLocation = dto.getActiveLocation();
        this.activePostCreatedAt = simpleDateFormat.format(now);
    }

    public void patch(PatchActivePostRequestDto dto) {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        this.activePostTitle = dto.getActivePostTitle();
        this.activePostImage = dto.getActivePostImage();
        this.activePostContent = dto.getActivePostContent();
        this.activeStartDate = dto.getActiveStartDate();
        this.activeEndDate = dto.getActiveEndDate();
        this.activePostCreatedAt = simpleDateFormat.format(now);
    }

}
