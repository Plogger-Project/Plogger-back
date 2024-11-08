package com.project.plogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.RecruitReportEntity;

import java.util.List;

@Repository
public interface RecruitReportRepository extends JpaRepository<RecruitReportEntity, Integer> {


    RecruitReportEntity findByRecruitId(Integer recruitId);
    RecruitReportEntity findByReportId(Integer recruitId);

    List<RecruitReportEntity> findAllByOrderByReportIdDesc();

    void deleteByRecruitId(Integer recruitId);

    boolean existsByRecruitId(Integer recruitId);
    boolean existsByRecruitIdAndUserId(Integer recruitId, String userId);
}
