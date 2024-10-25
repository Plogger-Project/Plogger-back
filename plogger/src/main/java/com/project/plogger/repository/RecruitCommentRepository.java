package com.project.plogger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.plogger.entity.RecruitCommentEntity;

public interface RecruitCommentRepository extends JpaRepository<RecruitCommentEntity, Integer>{
    
    RecruitCommentEntity findByRecruitCommentId(Integer recruitCommentId);
    List<RecruitCommentEntity> findByRecruitIdOrderByRecruitCommentIdAsc(Integer recruitId);

}
