package com.project.plogger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.plogger.entity.RecruitEntity;
import com.project.plogger.repository.resultset.CityPostCountResultSet;
import com.project.plogger.repository.resultset.GetRecruitResultSet;


public interface RecruitRepository extends JpaRepository<RecruitEntity, Integer> {
    
    List<RecruitEntity> findByOrderByRecruitPostIdDesc();

    RecruitEntity findByRecruitPostId(Integer recruitPostId);

    boolean existsByRecruitPostId(Integer recruitPostID);
    void deleteByRecruitPostId(Integer recruitPostId);
    
    @Query(value = "SELECT * FROM recruitpost WHERE recruit_post_id = :recruitPostId", nativeQuery = true)
    GetRecruitResultSet getRecruit(@Param("recruitPostId") Integer recruitPostId);

    List<RecruitEntity> findByRecruitPostWriterAndIsCompletedTrueAndIsMileageFalse(String userId);

    @Query(value = 
    "SELECT * FROM recruitpost WHERE recruit_post_id IN (" +
        "SELECT recruit_id FROM recruit_scrap WHERE user_id = :userId" +
    ")", nativeQuery=true)
    List<RecruitEntity> findScrapByUserId(@Param("userId") String userId);
    
    @Query(
        value = 
            "SELECT " +
                "SUBSTRING_INDEX(r.recruit_address, ' ', 1) AS city, " +
                "COUNT(r.recruit_post_id) AS postCount " +
            "FROM recruitpost r " +
            "WHERE r.recruit_address IS NOT NULL " +
            "GROUP BY city " +
            "ORDER BY postCount DESC",
        nativeQuery = true
    )
    List<CityPostCountResultSet> getCityPostCounts();

}




