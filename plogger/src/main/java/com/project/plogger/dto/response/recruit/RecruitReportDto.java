package com.project.plogger.dto.response.recruit;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecruitReportDto {
    private Integer reportId; // 신고 ID
    private String userId; // 사용자 ID
    private Integer recruitId; // 게시글 ID
    private String content; // 신고 내용
    private String createdAt; // 신고 일자
}
