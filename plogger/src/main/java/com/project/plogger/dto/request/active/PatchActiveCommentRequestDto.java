package com.project.plogger.dto.request.active;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchActiveCommentRequestDto {

    @NotBlank
    private String activeCommentContent;
    
}
