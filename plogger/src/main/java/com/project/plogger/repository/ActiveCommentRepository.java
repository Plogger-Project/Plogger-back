package com.project.plogger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.plogger.entity.ActiveCommentEntity;

public interface ActiveCommentRepository extends JpaRepository<ActiveCommentEntity, Integer>{
    
    ActiveCommentEntity findByActiveCommentId(Integer activeCommentId);
    List<ActiveCommentEntity> findByActiveIdOrderByActiveCommentIdAsc(Integer actveId);

}
