package com.project.plogger.dto.request.active;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchActivePostRequestDto {

    @NotBlank 
    private String activePostTitle;
    private String activePostImage;
    @NotBlank 
    private String activePostContent;
    @NotBlank 
    private String activeStartDate;
    @NotBlank 
    private String activeEndDate;
    @NotEmpty
    private List<String> activePeople;

}