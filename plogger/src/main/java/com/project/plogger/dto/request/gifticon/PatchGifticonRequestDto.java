package com.project.plogger.dto.request.gifticon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchGifticonRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String image;
    @NotNull
    private Integer mileageCost;
    
}
