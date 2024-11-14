package com.project.plogger.common.object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class LeaveRoom {
    private Integer roomId;
    private String userId;
    
    @JsonCreator
    public LeaveRoom(
        @JsonProperty("roomId") Integer roomId,
        @JsonProperty("userId") String userId
    ) {
        this.roomId = roomId;
        this.userId = userId;
    }
    
}
