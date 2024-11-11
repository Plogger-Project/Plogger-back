package com.project.plogger.common.object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRoom {
    private Integer roomId;

    @JsonCreator
    public JoinRoom(
        @JsonProperty("roomId") Integer roomId
    ) {
        this.roomId = roomId;
    }
}
