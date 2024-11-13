package com.project.plogger.common.object;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomInvite {
    private String senderId;
    private String[] inviteUsers;
}
