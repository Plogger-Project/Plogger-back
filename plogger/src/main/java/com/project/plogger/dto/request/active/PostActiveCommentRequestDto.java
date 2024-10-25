package com.project.plogger.dto.request.active;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostActiveCommentRequestDto {

    @NotBlank
    private String activeCommentContent;
    
}
