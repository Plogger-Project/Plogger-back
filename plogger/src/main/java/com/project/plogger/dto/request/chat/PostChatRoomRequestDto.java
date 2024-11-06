package com.project.plogger.dto.request.chat;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostChatRoomRequestDto {
    
    @NotNull
    private String roomName;

}
