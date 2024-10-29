package com.project.plogger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.RecruitJoinEntity;
import com.project.plogger.entity.pk.RecruitJoinPk;

import jakarta.transaction.Transactional;

@Repository
public interface RecruitJoinRepository extends JpaRepository<RecruitJoinEntity, RecruitJoinPk> {

    boolean existsByUserIdAndRecruitId(String userId, Integer recruitId);
    @Transactional
    void deleteByUserIdAndRecruitId(String userId, Integer recruitId);
    List<RecruitJoinEntity> findByRecruitId(Integer recruitId);

}
