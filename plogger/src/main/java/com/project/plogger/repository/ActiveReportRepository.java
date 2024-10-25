package com.project.plogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.ActiveReportEntity;

@Repository
public interface ActiveReportRepository extends JpaRepository<ActiveReportEntity, Integer>{
    ActiveReportEntity findByActiveId(Integer activeId);
}
