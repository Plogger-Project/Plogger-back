package com.project.plogger.dto.response;

public interface ResponseCode {
    
    String SUCCESS = "SU";
    String LIKE_CLICK = "LC";
    String LIKE_UNCLICK = "LUC";
    String SCRAP_CLICK = "SC";
    String SCRAP_UNCLICK = "SUC";
    String FULL_PEOPLE = "FP";
    
    String VALIDATION_FAILED = "VF";
    String DUPLICATED_USER_ID = "DI";
    String DUPLICATED_TEL_NUMBER = "DT";
    String NO_SELF_PARTICIPATION = "NSP";
    String NO_SELF_TAG = "NST";

    String NO_EXIST_USER_ID = "NI";
    String NO_EXIST_GIFTICON = "NG";
    String NO_EXIST_ACTIVE_POST = "NAP";
    String NO_EXIST_QNA_POST = "NQP";
    String NO_EXIST_QNA_COMMENT = "NQC";
    String NO_EXIST_ACTIVE_COMMENT = "NAC";
    String NO_EXIST_RECRUIT_COMMENT = "NRC";
    String NO_EXIST_TEL_NUMBER = "NT";
    String NO_EXIST_USER_ID_AND_TEL_NUMBER = "NIT";
    String PASSWORD_MISMATCH = "PM";
    String NO_EXIST_ACTIVE_TAG = "NAT";
    String NO_EXIST_ALERT = "NA";
    String NO_EXIST_FOLLOW = "NF";
    String NO_EXIST_RECRUIT_SCRAP = "NRS";

    String SIGN_IN_FAIL = "SF";
    String AUTHENTICATION_FAIL = "AF";

    String TEL_AUTH_FAIL = "TAF";
    String NO_PERMISSION = "NP";

    String MESSAGE_SEND_FAIL = "TF";
    String TOKEN_CREATE_FAIL = "TCF";
    String DATABASE_ERROR = "DBE";

    String NO_EXIST_RECRUIT_POST = "NRP";

}
