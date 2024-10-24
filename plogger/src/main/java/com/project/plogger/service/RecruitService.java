package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.recruit.PostRecruitRequestDto;
import com.project.plogger.dto.request.recruit.ReportRecruitReqeustDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitResponseDto;

public interface RecruitService {

    // ResponseEntity<ResponseDto> postRecruit(PostRecruitRequestDto dto);
    ResponseEntity<? super GetRecruitResponseDto> getRecruit(Integer recruitPostId);
    // ResponseEntity<ResponseDto> reportRecruit(Integer recruitPostId, String userId);
}
