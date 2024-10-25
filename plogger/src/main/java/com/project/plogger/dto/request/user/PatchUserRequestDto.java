package com.project.plogger.dto.request.user;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchUserRequestDto {

    private String profileImage;
    @Size(max = 20)
    private String name;
    @Pattern(regexp = "^[0-9]{11}$")
    private String telNumber;
    private String address;

}
