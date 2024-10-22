package com.project.plogger.dto.request.active;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostActivePostRequestDto {
    
    @NotBlank
    private String activePostTitle;
    private String activePostImage;
    @NotBlank
    private String activePostContent;
    @NotBlank
    private String activeStartDate;
    @NotBlank
    private String activeEndDate;
    @NotBlank
    private String activeLocation;

}
