package com.project.plogger.dto.request.recruit;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchRecruitIsCompletedRequestDto {

@NotNull
private Boolean isCompleted;
}
