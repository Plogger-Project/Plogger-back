package com.project.plogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    boolean existsByUserId(String userId);
    boolean existsByTelNumber(String telNumber);

}
