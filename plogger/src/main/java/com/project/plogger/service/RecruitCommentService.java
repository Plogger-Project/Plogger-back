package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.recruit.PatchRecruitCommentRequestDto;
import com.project.plogger.dto.request.recruit.PostRecruitCommentRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitCommentListResponseDto;

public interface RecruitCommentService {

    ResponseEntity<ResponseDto> postRecruitComment(PostRecruitCommentRequestDto dto, String userId, Integer recruitId);
    ResponseEntity<? super GetRecruitCommentListResponseDto> getRecruitCommentList(Integer recruitPostId);
    ResponseEntity<ResponseDto> patchRecruitComment(Integer recruitId, Integer recruitCommentId, String userId, PatchRecruitCommentRequestDto dto);
    ResponseEntity<ResponseDto> deleteRecruitComment(Integer recruitId, Integer recruitCommentId, String userId);
    
}
