package com.project.plogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.QnAEntity;

@Repository
public interface QnARepository extends JpaRepository<QnAEntity, Integer> {

}
