package com.project.plogger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.QnAEntity;

@Repository
public interface QnARepository extends JpaRepository<QnAEntity, Integer> {

    List<QnAEntity> findByOrderByQnaPostIdDesc();

}
