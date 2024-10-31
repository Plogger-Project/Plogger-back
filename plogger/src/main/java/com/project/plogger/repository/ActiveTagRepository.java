package com.project.plogger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.ActiveTagEntity;
import com.project.plogger.entity.pk.ActiveTagPk;


@Repository
public interface ActiveTagRepository extends JpaRepository<ActiveTagEntity, ActiveTagPk> {

    List<ActiveTagEntity> findByActiveId(Integer activeId);
    ActiveTagEntity findByUserIdAndActiveId(String userId, Integer activeId);

}
