package com.project.plogger.dto.response;

public interface ResponseMessage {

    String SUCCESS = "Success.";

    String VALIDATION_FAIL = "Validation failed.";
    String DUPLICATED_USER_ID = "Duplicated user id.";
    String DUPLICATED_TEL_NUMBER = "Duplicated user tel number.";
    String NO_EXIST_Gifticon = "No exist gifticon";

    String TEL_AUTH_FAIL = "Tel number authentication failed.";

    String MESSAGE_SEND_FAIL = "Auth number send failed.";
    String DATABASE_ERROR = "Database error.";

}
