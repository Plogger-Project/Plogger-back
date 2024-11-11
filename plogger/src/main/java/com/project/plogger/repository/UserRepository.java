package com.project.plogger.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.project.plogger.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    
    boolean existsByUserId(String userId);
    boolean existsByTelNumber(String telNumber); 
    boolean existsByTelNumberAndUserId(String telNumber, String userId);
    
    UserEntity findByUserId(String userId);
    UserEntity findByTelNumber(String telNumber);

    UserEntity findByUserIdAndTelNumber(String userId, String telNumber);

    UserEntity findBySnsIdAndJoinPath(String snsId, String joinPath);
    
    @Query(value= 
    "SELECT * " +
    "FROM user " +
    "WHERE user_id IN ( " +
        "SELECT user_id FROM recruit_join " +
        "WHERE recruit_id = :recruitId" +
            ")",
    nativeQuery=true
    )
    List<UserEntity> findJoinByRecruitId(@Param("recruitId") Integer recruitId);

    void deleteByUserId(String userId);
    
    
}
