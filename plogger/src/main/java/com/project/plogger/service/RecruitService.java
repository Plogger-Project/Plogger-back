package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.recruit.PatchRecruitRequestDto;
import com.project.plogger.dto.request.recruit.PostRecruitRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitListResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitResponseDto;

public interface RecruitService {

    ResponseEntity<ResponseDto> postRecruit(PostRecruitRequestDto dto,String userId);

    ResponseEntity<? super GetRecruitResponseDto> getRecruit(Integer recruitPostId);

    ResponseEntity<? super GetRecruitListResponseDto> getRecruitList();

    ResponseEntity<ResponseDto> patchRecruit(Integer recruitPostId, String userId, PatchRecruitRequestDto dto);

    ResponseEntity<ResponseDto> deleteRecruit(Integer recruitPostId, String userId);
    
}
