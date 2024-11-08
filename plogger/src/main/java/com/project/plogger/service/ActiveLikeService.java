package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.like.GetActiveLikeResponseDto;

public interface ActiveLikeService {

    ResponseEntity<ResponseDto> activeLike(String userId, Integer activeId); 
    ResponseEntity<? super GetActiveLikeResponseDto> getLike(Integer activeId);

}
