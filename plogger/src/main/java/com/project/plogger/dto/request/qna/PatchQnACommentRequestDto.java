package com.project.plogger.dto.request.qna;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchQnACommentRequestDto {

    @NotBlank
    private String qnaCommentContent;
    
}
