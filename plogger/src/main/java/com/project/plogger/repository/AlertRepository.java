package com.project.plogger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.plogger.entity.alert.AlertEntity;

public interface AlertRepository extends JpaRepository<AlertEntity, Long> {
    AlertEntity findByUserId(String userId);
    List<AlertEntity> findByUserIdOrderByIdDesc(String userId);
}