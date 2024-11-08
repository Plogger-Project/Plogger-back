package com.project.plogger.dto.response;

public interface ResponseMessage {

    String SUCCESS = "Success.";
    String LIKE_CLICK = "Like click.";
    String LIKE_UNCLICK = "Like unclick.";
    String SCRAP_CLICK = "Scrap click.";
    String SCRAP_UNCLICK = "Scrap unclick.";
    String FULL_PEOPLE = "full people.";

    String VALIDATION_FAIL = "Validation failed.";
    String DUPLICATED_USER_ID = "Duplicated user id.";
    String DUPLICATED_TEL_NUMBER = "Duplicated user tel number.";
    String DUPLICATED_REPORT = "Duplicated Report";
    String NO_SELF_PARTICIPATION = "No self participation.";
    String NO_SELF_TAG = "No self tag";

    String NO_EXIST_GIFTICON = "No exist gifticon";
    String NO_EXIST_USER_ID = "No exist user id.";
    String NO_EXIST_ACTIVE_POST = "No exist active post.";
    String NO_EXIST_QNA_POST = "No exist qna post.";
    String NO_EXIST_RECRUIT_POST = "No exist Recruit Post";
    String NO_EXIST_QNA_COMMENT = "No exist qna comment.";
    String NO_EXIST_ACTIVE_COMMENT = "No exist active comment.";
    String NO_EXIST_RECRUIT_COMMENT = "No exist recruit comment.";
    String NO_EXIST_ACTIVE_TAG = "No exist active tag.";
    String NO_EXIST_CHAT_ROOM = "No exist chat room";

    String NO_EXIST_TEL_NUMBER = "No exist tel number";
    String NO_EXIST_USER_ID_AND_TEL_NUMBER = "No exist user id and tel number";
    String NO_EXIST_ALERT = "No exist alert";
    String NO_EXIST_FOLLOW = "No exist follow.";
    String NO_EXIST_RECRUIT_SCRAP = "No exist recruit scrap.";

    String NO_EXIST_RECRUIT_REPORT = "No exist recruit report";
    String NO_EXIST_ACTIVE_REPORT = "No exist active report";

    String SIGN_IN_FAIL = "Sign in failed.";
    String AUTHENTICATION_FAIL = "Authentication failed.";
    String PASSWORD_MISMATCH = "Password mismatch.";

    String TEL_AUTH_FAIL = "Tel number authentication failed.";
    String NO_PERMISSION = "No permission.";

    String MESSAGE_SEND_FAIL = "Auth number send failed.";
    String TOKEN_CREATE_FAIL = "Token creation failed.";
    String DATABASE_ERROR = "Database error.";

}
