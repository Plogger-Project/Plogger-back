package com.project.plogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.RecruitReportEntity;

import java.util.List;

@Repository
public interface RecruitReportRepository extends JpaRepository<RecruitReportEntity, Integer> {
    RecruitReportEntity findByRecruitId(Integer recruitId);

    List<RecruitReportEntity> findAllByOrderbyRecruitReportDesc();
}
