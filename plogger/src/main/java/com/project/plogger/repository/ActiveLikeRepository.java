package com.project.plogger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.ActiveLikeEntity;
import com.project.plogger.entity.pk.ActiveLikePk;

import jakarta.transaction.Transactional;

@Repository
public interface ActiveLikeRepository extends JpaRepository<ActiveLikeEntity, ActiveLikePk> {

    boolean existsByUserIdAndActiveId(String userId, Integer activeId);
    @Transactional
    void deleteByUserIdAndActiveId(String userId, Integer activeId);
    List<ActiveLikeEntity> findByActiveId(Integer activeId);

}
