package com.project.plogger.dto.request.active;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchActivePostRequestDto {

    @NotNull
    private String activePostTitle;
    private String activePostImage;
    @NotNull
    private String activePostContent;
    @NotNull
    private String activeStartDate;
    @NotNull 
    private String activeEndDate;

}
