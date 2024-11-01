package com.project.plogger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.MileageEntity;

@Repository
public interface MileageRepository extends JpaRepository<MileageEntity, Integer> {

    MileageEntity findByUserId(String userId);
    List<MileageEntity> findByUserIdOrderByMileageIdDesc(String userId);

}
