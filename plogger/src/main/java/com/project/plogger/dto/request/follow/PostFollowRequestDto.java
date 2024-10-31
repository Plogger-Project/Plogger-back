package com.project.plogger.dto.request.follow;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostFollowRequestDto {

    @NotBlank
    private String followeeId;
    
}
