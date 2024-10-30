package com.project.plogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.MileageEntity;

@Repository
public interface MileageRepository extends JpaRepository<MileageEntity, Integer> {

    
    
}
