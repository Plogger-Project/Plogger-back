package com.project.plogger.dto.request.alert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlertRequestDto {
    private String userId;
    private String message;
}
