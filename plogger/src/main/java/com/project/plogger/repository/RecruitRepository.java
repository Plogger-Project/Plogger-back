package com.project.plogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.plogger.entity.RecruitEntity;
import com.project.plogger.repository.resultSet.GetRecruitResultSet;

public interface RecruitRepository extends JpaRepository<RecruitEntity, Integer> {
    
    RecruitEntity findByRecruitPostId(Integer recruitPostId);

    boolean existsByRecruitPostId(Integer recruitPostID);
    void deleteByRecruitPostId(Integer recruitPostId);
    
    @Query(value = "SELECT * FROM recruitpost WHERE recruit_post_id = :recruitPostId", nativeQuery = true)
    GetRecruitResultSet getRecruit(@Param("recruitPostId") Integer recruitPostId);
}
