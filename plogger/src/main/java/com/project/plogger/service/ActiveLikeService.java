package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseDto;

public interface ActiveLikeService {

    ResponseEntity<ResponseDto> activeLike(String userId, Integer activeId); 

}
