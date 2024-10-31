package com.project.plogger.dto.response;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.entity.RecruitReportEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {
    
    private String code;
    private String message;
    

    public static ResponseEntity<ResponseDto> success() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> likeClick() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.LIKE_CLICK, ResponseMessage.LIKE_CLICK);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> likeUnclick() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.LIKE_UNCLICK, ResponseMessage.LIKE_UNCLICK);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> scrapClick() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.SCRAP_CLICK, ResponseMessage.SCRAP_CLICK);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> scrapUnclick() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.SCRAP_UNCLICK, ResponseMessage.SCRAP_UNCLICK);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> fullPeople() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.FULL_PEOPLE, ResponseMessage.FULL_PEOPLE);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> signInFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> passwordMismatch() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.PASSWORD_MISMATCH, ResponseMessage.PASSWORD_MISMATCH);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> validationFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.VALIDATION_FAILED, ResponseMessage.VALIDATION_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> AuthenticationFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.AUTHENTICATION_FAIL, ResponseMessage.AUTHENTICATION_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> duplicatedUserId() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATED_USER_ID, ResponseMessage.DUPLICATED_USER_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> duplicatedTelNumber() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATED_TEL_NUMBER, ResponseMessage.DUPLICATED_TEL_NUMBER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noSelfParticipation() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_SELF_PARTICIPATION, ResponseMessage.NO_SELF_PARTICIPATION);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noSelfTag() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_SELF_TAG, ResponseMessage.NO_SELF_TAG);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noExistGifticon() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_GIFTICON, ResponseMessage.NO_EXIST_GIFTICON);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noExistUserId() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_USER_ID, ResponseMessage.NO_EXIST_USER_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noExistTelNumber() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_TEL_NUMBER, ResponseMessage.NO_EXIST_TEL_NUMBER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noExistUserIdAndTelNumber() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_USER_ID_AND_TEL_NUMBER, ResponseMessage.NO_EXIST_USER_ID_AND_TEL_NUMBER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noExistActivePost() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_ACTIVE_POST, ResponseMessage.NO_EXIST_ACTIVE_POST);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noExistQnA() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_QNA_POST, ResponseMessage.NO_EXIST_QNA_POST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noExistQnAComment() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_QNA_COMMENT, ResponseMessage.NO_EXIST_QNA_COMMENT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noExistActiveComment() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_ACTIVE_COMMENT, ResponseMessage.NO_EXIST_ACTIVE_COMMENT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noExistRecruitComment() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_RECRUIT_COMMENT, ResponseMessage.NO_EXIST_RECRUIT_COMMENT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noExistActiveTag() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_ACTIVE_TAG, ResponseMessage.NO_EXIST_ACTIVE_TAG);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noExistAlertsFound() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_ALERT, ResponseMessage.NO_EXIST_ALERT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noExistFollow() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_FOLLOW, ResponseMessage.NO_EXIST_FOLLOW);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> telAuthFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.TEL_AUTH_FAIL, ResponseMessage.TEL_AUTH_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> noPermission() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_PERMISSION, ResponseMessage.NO_PERMISSION);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> messageSendFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.MESSAGE_SEND_FAIL, ResponseMessage.MESSAGE_SEND_FAIL);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> tokenCreateFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.TOKEN_CREATE_FAIL, ResponseMessage.TOKEN_CREATE_FAIL);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> databaseError() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
    
    public static ResponseEntity<ResponseDto> noExistRecruit() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_EXIST_RECRUIT_POST,
                ResponseMessage.NO_EXIST_RECRUIT_POST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
