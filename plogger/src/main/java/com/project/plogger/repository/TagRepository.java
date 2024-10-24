package com.project.plogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.PostTagEntity;
import com.project.plogger.entity.pk.PostTagPk;

@Repository
public interface TagRepository extends JpaRepository<PostTagEntity, PostTagPk> {
    
}
