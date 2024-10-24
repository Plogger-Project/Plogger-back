package com.project.plogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.RecruitLikeEntity;
import com.project.plogger.entity.pk.RecruitLikePk;

import jakarta.transaction.Transactional;

@Repository
public interface RecruitLikeRepository extends JpaRepository<RecruitLikeEntity, RecruitLikePk> {
    
    boolean existsByUserIdAndRecruitId(String userId, Integer recruitId);
    @Transactional
    void deleteByUserIdAndRecruitId(String userId, Integer recruitId);

}
