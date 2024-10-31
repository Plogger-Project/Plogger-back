package com.project.plogger.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.FollowEntity;

@Repository
public interface FollowRepository extends JpaRepository<FollowEntity, Integer> {

    FollowEntity findByFollowerIdAndFolloweeId(String followerId, String followeeId);
    List<FollowEntity> findByFolloweeIdOrderByFollowIdDesc(String followeeId);
    List<FollowEntity> findByFollowerIdOrderByFollowIdDesc(String followerId);

}
