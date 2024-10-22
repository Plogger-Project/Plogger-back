package com.project.plogger.dto.request.scrap;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRecruitScrapRequestDto {

    @NotBlank
    private String userId;
    @NotBlank
    private String recruitId;
    @NotBlank
    private String createdAt;

}
