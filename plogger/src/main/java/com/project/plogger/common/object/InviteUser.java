package com.project.plogger.common.object;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class InviteUser {
    private Integer roomId;
    private String[] invitedPeople;

    @JsonCreator
    public InviteUser(
        @JsonProperty("roomId") Integer roomId,
        @JsonProperty("invitedPeople") String[] invitedPeople
    ) {
        this.roomId = roomId;
        this.invitedPeople = invitedPeople;
    }
}