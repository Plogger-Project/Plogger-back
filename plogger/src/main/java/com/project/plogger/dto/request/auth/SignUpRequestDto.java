package com.project.plogger.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
    
    @NotBlank
    @Size(max = 20)
    private String userId;
    @NotBlank
    @Size(max = 20)
    private String name;
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,13}$")
    private String password;
    @NotBlank
    @Pattern(regexp = "^[0-9]{11}$")
    private String telNumber;
    @NotBlank
    private String authNumber;
    @NotBlank
    private String address;
    @NotBlank
    private String profileImage;
    @NotNull
    private Integer ecoScore;
    @NotNull
    private Integer mileage;
    private String comment;
    @NotBlank
    @Pattern(regexp = "^(home|kakao|naver|google)$")
    private String joinPath;
    private String snsId;
    @NotNull
    private Boolean isAdmin;

}
