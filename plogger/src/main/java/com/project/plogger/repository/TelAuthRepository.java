package com.project.plogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.TelAuthEntity;


@Repository
public interface TelAuthRepository extends JpaRepository<TelAuthEntity, String> {
    
    TelAuthEntity findByTelNumber(String telNumber);
    boolean existsByTelNumberAndAuthNumber(String telNumber, String authNumber);

}
