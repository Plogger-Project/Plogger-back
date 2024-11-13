package com.project.plogger.dto.request.alert;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertRequestDto {
    @NotBlank
    private String userId;
    @NotBlank
    private String message;
    private Integer recruitPostId;
    private Integer activePostId;
    private Integer qnaPostId;
}
