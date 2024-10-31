package com.project.plogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.plogger.entity.MileageEntity;

public interface MileageRepository extends JpaRepository<MileageEntity, Integer> {

    MileageEntity findByUserId(String userId);

}
