package com.project.plogger.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.project.plogger.dto.request.active.PatchActiveCommentRequestDto;
import com.project.plogger.dto.request.active.PostActiveCommentRequestDto;

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
@Table(name = "activecomment")
@Entity(name = "activecomment")
public class ActiveCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activeCommentId;
    private Integer activeId;
    private String activeCommentWriter;
    private String activeCommentCreatedAt;
    private String activeCommentContent;

    public ActiveCommentEntity(PostActiveCommentRequestDto dto) {
        this.activeCommentContent = dto.getActiveCommentContent();
    }

    private static final DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void setActiveCommentCreatedAt() {
        this.activeCommentCreatedAt = LocalDateTime.now().format(Formatter);
    }

    public void patch(PatchActiveCommentRequestDto dto) {
        this.activeCommentContent = dto.getActiveCommentContent();
    }
    
}
