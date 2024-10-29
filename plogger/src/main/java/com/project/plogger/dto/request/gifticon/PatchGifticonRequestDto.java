package com.project.plogger.dto.request.gifticon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchGifticonRequestDto {

    private String name;
    private String image;
    private Integer mileageCost;
    
}
