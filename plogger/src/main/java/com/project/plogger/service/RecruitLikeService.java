package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.like.GetRecruitLikeResponseDto;

public interface RecruitLikeService {

    ResponseEntity<ResponseDto> recruitLike(String userId, Integer recruitId); 
    ResponseEntity<? super GetRecruitLikeResponseDto> getLike(Integer recruitId);
    
}
