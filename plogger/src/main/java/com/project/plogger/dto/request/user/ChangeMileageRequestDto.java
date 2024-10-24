package com.project.plogger.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeMileageRequestDto {

    @NotBlank
    private String userId;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    private String telNumber;
    @NotBlank
    private String address;
    private String profileImage;
    @NotNull
    private Boolean isAdmin;
    @NotNull
    private Integer ecoScore;
    @NotNull
    private Integer mileage;
    private String comment;

}
