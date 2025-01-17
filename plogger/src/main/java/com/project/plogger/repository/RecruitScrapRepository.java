package com.project.plogger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.RecruitScrapEntity;
import com.project.plogger.entity.pk.RecruitScrapPk;

import jakarta.transaction.Transactional;

@Repository
public interface RecruitScrapRepository extends JpaRepository<RecruitScrapEntity, RecruitScrapPk> {
    
    boolean existsByUserIdAndRecruitId(String userId, Integer recruitId);
    @Transactional
    void deleteByUserIdAndRecruitId(String userId, Integer recruitId);

    List<RecruitScrapEntity> findByUserIdOrderByCreatedAtDesc(String userId);
    List<RecruitScrapEntity> findByRecruitId(Integer recruitId);
    RecruitScrapEntity findByUserIdAndRecruitId(String userId, Integer recruitId);

}
