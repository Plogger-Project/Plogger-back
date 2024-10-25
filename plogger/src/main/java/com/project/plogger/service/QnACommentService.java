package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.qna.PatchQnACommentRequestDto;
import com.project.plogger.dto.request.qna.PostQnACommentRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.qna.GetQnACommentListResponseDto;

public interface QnACommentService {

    ResponseEntity<ResponseDto> postQnAComment(PostQnACommentRequestDto dto, String userId, Integer qnaId);
    ResponseEntity<? super GetQnACommentListResponseDto> getQnACommentList(Integer qnaPostId);
    ResponseEntity<ResponseDto> patchQnAComment(Integer qnaId, Integer qnaCommentId, String userId, PatchQnACommentRequestDto dto);

    ResponseEntity<ResponseDto> deleteQnAComment(Integer qnaId, Integer qnaCommentId, String userId);  
}
