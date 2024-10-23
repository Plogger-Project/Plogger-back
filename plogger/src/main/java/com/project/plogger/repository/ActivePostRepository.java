package com.project.plogger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.ActivePostEntity;

@Repository
public interface ActivePostRepository extends JpaRepository<ActivePostEntity, Integer> {
    
    ActivePostEntity findByActivePostId(Integer activePostId);
    boolean existsByActivePostId(Integer activePostId);
    void deleteByActivePostId(Integer activePostId);
    List<ActivePostEntity> findAllByOrderByActivePostIdDesc();

}
