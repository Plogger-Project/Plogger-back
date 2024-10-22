package com.project.plogger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.plogger.entity.QnACommentEntity;

public interface QnACommentRepository extends JpaRepository<QnACommentEntity, Integer>{

    List<QnACommentEntity> findByQnaIdOrderByQnaCommentIdAsc(Integer qnaId);

}
