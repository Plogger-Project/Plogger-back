package com.project.plogger.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MileageTagRemoveDto {
    private String userId;
    private Integer activeId;
    private Integer mileageResult;
}
