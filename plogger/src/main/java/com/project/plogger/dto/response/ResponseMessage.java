package com.project.plogger.dto.response;

public interface ResponseMessage {

    String SUCCESS = "Success.";

    String VALIDATION_FAIL = "Validation failed.";
    String DUPLICATED_USER_ID = "Duplicated user id.";
    String DUPLICATED_TEL_NUMBER = "Duplicated user tel number.";
    String NO_EXIST_USER_ID = "No exist user id.";

    String TEL_AUTH_FAIL = "Tel number authentication failed.";
<<<<<<< HEAD
    String NO_PERMISSION = "No permission.";
=======
    String SIGN_IN_FAIL = "Sign in failed.";
    String AUTHENTICATION_FAIL = "ㅇㄹㅇㄹㅇ.";
>>>>>>> 7570330adf36d1be952764a62e530432916e6887

    String MESSAGE_SEND_FAIL = "Auth number send failed.";
    String TOKEN_CREATE_FAIL = "Token creation failed.";
    String DATABASE_ERROR = "Database error.";

}
