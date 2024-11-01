package com.project.plogger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.ActiveReportEntity;

@Repository
public interface ActiveReportRepository extends JpaRepository<ActiveReportEntity, Integer>{
    ActiveReportEntity findByActiveId(Integer activeId);
    List<ActiveReportEntity> findAllByOrderByReportIdAsc();
    boolean existsByActiveIdAndUserId(Integer activeId, String userId);
}
