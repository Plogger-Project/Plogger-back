package com.project.plogger.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.project.plogger.dto.request.qna.PatchQnACommentRequestDto;
import com.project.plogger.dto.request.qna.PostQnACommentRequestDto;

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
@Table(name = "qnacomment")
@Entity(name = "qnacomment")
public class QnACommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer qnaCommentId;
    private Integer qnaId;
    private String qnaCommentWriter;
    private String qnaCommentCreatedAt;
    private String qnaCommentContent;

    public QnACommentEntity(PostQnACommentRequestDto dto) {
        this.qnaCommentContent = dto.getQnaCommentContent();
    }

    private static final DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void setQnaCommentCreatedAt() {
        this.qnaCommentCreatedAt = LocalDateTime.now().format(Formatter);
    }

    public void patch(PatchQnACommentRequestDto dto) {
        this.qnaCommentContent = dto.getQnaCommentContent();
    }
    
}
