package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitJoinListResponseDto;

public interface RecruitJoinService {
    
    ResponseEntity<ResponseDto> recruitJoin(String userId, Integer recruitId);

    ResponseEntity<? super GetRecruitJoinListResponseDto> getRecruitJoinList(String userId, Integer recruitId);
}
