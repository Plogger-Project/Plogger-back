package com.project.plogger.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.recruit.PatchRecruitIsCompletedRequestDto;
import com.project.plogger.dto.request.recruit.PatchRecruitRequestDto;
import com.project.plogger.dto.request.recruit.PostRecruitRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitCityCountResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitListResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitResponseDto;
import com.project.plogger.repository.resultset.CityPostCountResultSet;

public interface RecruitService {

    ResponseEntity<ResponseDto> postRecruit(PostRecruitRequestDto dto,String userId);

    ResponseEntity<? super GetRecruitResponseDto> getRecruit(Integer recruitPostId);

    ResponseEntity<? super GetRecruitResponseDto> getProfileImage(Integer recruitPostId);

    ResponseEntity<? super GetRecruitListResponseDto> getRecruitList();

    ResponseEntity<ResponseDto> patchRecruit(Integer recruitPostId, String userId, PatchRecruitRequestDto dto);

    ResponseEntity<ResponseDto> patchRecruitIsCompleted(Integer recruitPostId, String userId, PatchRecruitIsCompletedRequestDto dto);

    ResponseEntity<ResponseDto> deleteRecruit(Integer recruitPostId, String userId);

    ResponseEntity<? super GetRecruitCityCountResponseDto> getCityPostCounts();

}
