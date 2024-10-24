package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.recruit.ReportRecruitReqeustDto;
import com.project.plogger.dto.response.recruit.GetRecruitResponseDto;
import com.project.plogger.provider.JwtProvider;
import com.project.plogger.service.RecruitService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recruit")
@RequiredArgsConstructor
public class RecruitController {

    private final RecruitService recruitService;
    private final JwtProvider jwtProvider;

    @GetMapping(value = {"/{recruitPostId}"})
    public ResponseEntity<? super GetRecruitResponseDto> getRecruit(
        @PathVariable("recruitPostId") Integer recruitPostId
    ) {
        ResponseEntity<? super GetRecruitResponseDto> response = recruitService.getRecruit(recruitPostId);
        return response;
    }

    // @PostMapping(value = "/{recruitPostId}/report")
    // public ResponseEntity<String> reportRecruit(
    //     @PathVariable("recruitPostId") Integer RecruitPostId,
    //     @RequestHeader("Authorization") String token,
    //     @RequestBody ReportRecruitReqeustDto reportRecruitReqeustDto
    // ) {
    //     String userId = jwtProvider.getUserIdFromToken(token);
    //     recruitService.reportRecruit(RecruitPostId, userId);
    //     return ResponseEntity.ok("게시글이 신고되었습니다.");
    // }
}
