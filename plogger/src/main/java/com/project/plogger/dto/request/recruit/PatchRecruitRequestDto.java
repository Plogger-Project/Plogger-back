package com.project.plogger.dto.request.recruit;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchRecruitRequestDto {
@NotBlank
    private String recruitPostTitle;
    @NotBlank
    private String recruitPostContent;
    private String recruitPostImage;
    @NotBlank
    private String recruitEndDate;
    @Min(1)
    private Integer minPeople;
}
