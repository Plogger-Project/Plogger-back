package com.project.plogger.service.active;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.active.PatchActivePostRequestDto;
import com.project.plogger.dto.request.active.PostActivePostRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.active.GetActivePostListResponseDto;
import com.project.plogger.dto.response.active.GetActivePostResponseDto;

public interface ActivePostService {
    
    ResponseEntity<ResponseDto> postActivePost(PostActivePostRequestDto dto, String userId);
    ResponseEntity<ResponseDto> patchActivePost(PatchActivePostRequestDto dto, Integer activePostId, String userId);
    ResponseEntity<ResponseDto> deleteActivePost(Integer activePostId, String userId);
    ResponseEntity<? super GetActivePostListResponseDto> getActivePostList();
    ResponseEntity<? super GetActivePostResponseDto> getActivePost(Integer activePostId);

}
