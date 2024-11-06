package com.project.plogger.dto.request.chat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostChatMessageRequestDto {

    @NotNull
    private String receiverId;
    @NotNull
    private String message;

}