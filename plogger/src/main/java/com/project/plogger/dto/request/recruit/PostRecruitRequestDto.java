package com.project.plogger.dto.request.recruit;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRecruitRequestDto {
    @NotBlank
    private String recruitPostTitle;
    @NotBlank
    private String recruitPostContent;

    private String recruitPostImage;
    @NotBlank
    private String recruitPostWriter;
    @NotBlank
    private String recruitPostCreatedAt;
    @NotBlank
    private String recruitLocation;
    @NotBlank
    private String recruitEndDate;
    @NotBlank
    private Integer minPeople;
    @NotBlank
    private Integer currentPeople;
    @NotBlank
    private Integer recruitView;
    @NotBlank
    private Integer recruitPostLike;
    @NotBlank
    private Integer recruitReport;
    @NotBlank
    private Integer isCompleted;
}
