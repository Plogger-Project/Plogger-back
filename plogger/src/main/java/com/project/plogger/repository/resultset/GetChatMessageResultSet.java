package com.project.plogger.repository.resultset;

public interface GetChatMessageResultSet {
    Integer getChatId();
    String getSenderId();
    Integer getRoomId();
    String getMessage();
    String getSentAt();
    Byte getIsRead();
}
