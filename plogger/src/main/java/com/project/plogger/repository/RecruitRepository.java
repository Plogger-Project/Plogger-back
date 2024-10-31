package com.project.plogger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.plogger.dto.response.recruit.GetRecruitResponseDto;
import com.project.plogger.entity.RecruitEntity;
import com.project.plogger.repository.resultset.GetRecruitResultSet;


public interface RecruitRepository extends JpaRepository<RecruitEntity, Integer> {
    
    List<RecruitEntity> findByOrderByRecruitPostIdDesc();

    RecruitEntity findByRecruitPostId(Integer recruitPostId);

    boolean existsByRecruitPostId(Integer recruitPostID);
    void deleteByRecruitPostId(Integer recruitPostId);
    
    @Query(value = "SELECT * FROM recruitpost WHERE recruit_post_id = :recruitPostId", nativeQuery = true)
    GetRecruitResultSet getRecruit(@Param("recruitPostId") Integer recruitPostId);

    List<RecruitEntity> findByRecruitPostWriterAndIsCompletedTrue(String userId);

    // @Query(value = "SELECT " +
    //         "u.profile_image " +
    //         "FROM recruitpost rp " +
    //         "JOIN user u " +
    //         "ON rp.recruit_post_writer=u.user_id " +
    //         "WHERE rp.recruit_post_id=  :recruitPostId "
    //         , nativeQuery = true)
    // String getProfileImage(@Param("recruitPostId") Integer recruitPostId);

  


}
