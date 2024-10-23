package com.project.plogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.ActiveLikeEntity;
import com.project.plogger.entity.pk.ActiveLikePk;

import jakarta.transaction.Transactional;

@Repository
public interface ActiveLikeRepository extends JpaRepository<ActiveLikeEntity, ActiveLikePk> {

    boolean existsByUserIdAndActiveId(String userId, Integer activeId);
    @Transactional
    void deleteByUserIdAndActiveId(String userId, Integer activeId);

}
