package com.project.plogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.RecruitScrapEntity;
import com.project.plogger.entity.pk.RecruitScrapPk;

@Repository
public interface RecruitScrapRepository extends JpaRepository<RecruitScrapEntity, RecruitScrapPk> {
    
    RecruitScrapEntity findByUserIdAndRecruitId(String userId, String recruitId);

}
