package com.project.plogger.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.project.plogger.dto.request.recruit.PostRecruitCommentRequestDto;

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
@Table(name = "recruitcomment")
@Entity(name = "recruitcomment")
public class RecruitCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recruitCommentId;
    private Integer recruitId;
    private String recruitCommentWriter;
    private String recruitCommentCreatedAt;
    private String recruitCommentContent;

    public RecruitCommentEntity(PostRecruitCommentRequestDto dto) {
        this.recruitCommentContent = dto.getRecruitCommentContent();
    }

    private static final DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void setRecruitCommentCreatedAt() {
        this.recruitCommentCreatedAt = LocalDateTime.now().format(Formatter);
    }

    // public void patch(PatchRecruitCommentRequestDto dto) {
    //     this.recruitCommentContent = dto.getRecruitCommentContent();
    // }
    
}
