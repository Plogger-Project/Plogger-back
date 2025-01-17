package com.project.plogger.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.plogger.entity.GifticonEntity;

@Repository
public interface GifticonRepository extends JpaRepository<GifticonEntity, Integer>{

    GifticonEntity findByGifticonId(Integer gifticonId);
    List<GifticonEntity> findByOrderByGifticonIdDesc();    
}
