package com.project.plogger.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.project.plogger.dto.request.qna.PatchQnARequestDto;
import com.project.plogger.dto.request.qna.PostQnARequestDto;

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
@Table(name = "qnapost")
@Entity(name = "qnapost")
public class QnAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer qnaPostId;
    private String qnaPostTitle;
    private String qnaPostContent;
    private String qnaPostImage;
    private String qnaPostWriter;
    private String qnaPostCreatedAt;
    private Boolean isPinned;

    private static final DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void setQnaPostCreatedAt() {
        this.qnaPostCreatedAt = LocalDateTime.now().format(Formatter);
    }

    public QnAEntity(PostQnARequestDto dto) {
        this.qnaPostTitle = dto.getQnaPostTitle();
        this.qnaPostContent = dto.getQnaPostContent();
        this.qnaPostImage = dto.getQnaPostImage();
        this.isPinned = dto.getIsPinned();
    }

    public void patch(PatchQnARequestDto dto) {
        this.qnaPostTitle = dto.getQnaPostTitle();
        this.qnaPostContent = dto.getQnaPostContent();
        this.qnaPostImage = dto.getQnaPostImage();
        this.isPinned = dto.getIsPinned();
    }
    
}
