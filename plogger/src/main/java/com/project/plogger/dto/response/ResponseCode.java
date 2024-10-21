package com.project.plogger.dto.response;

public interface ResponseCode {
    
    String SUCCESS = "SU";
    
    String VALIDATION_FAILED = "VF";
    String DUPLICATED_USER_ID = "DI";
    String DUPLICATED_TEL_NUMBER = "DT";
    String NO_EXIST_Gifticon = "NG";


    String TEL_AUTH_FAIL = "TAF";

    String MESSAGE_SEND_FAIL = "TF";
    String DATABASE_ERROR = "DBE";

}
