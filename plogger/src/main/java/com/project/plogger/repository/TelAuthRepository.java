package com.project.plogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.TelAuth;

@Repository
public interface TelAuthRepository extends JpaRepository<TelAuth, String> {
    
    boolean existsByTelNumberAndAuthNumber(String telNumber, String authNumber);

}
