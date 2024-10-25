package com.project.plogger.dto.request.recruit;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRecruitCommentRequestDto {

    @NotBlank
    private String recruitCommentContent;
    
}
