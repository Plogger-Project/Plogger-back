package com.project.plogger.dto.request.qna;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchQnARequestDto {

    @NotBlank
    private String qnaPostTitle;
    @NotBlank
    private String qnaPostContent;
    private String qnaPostImage;
    @NotNull
    private Boolean isPinned;
    
}
