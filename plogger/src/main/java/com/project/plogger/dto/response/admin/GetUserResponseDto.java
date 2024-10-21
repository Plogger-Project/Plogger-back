package com.project.plogger.dto.response.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.User;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.UserEntity;

import lombok.Getter;

@Getter
public class GetUserResponseDto extends ResponseDto {
    private String userId;
    private String name;
    private String telNumber;

    private GetUserResponseDto(UserEntity userEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = userEntity.getUserId();
        this.name = userEntity.getName();
        this.telNumber = userEntity.getTelNumber();
    }

    public static ResponseEntity<GetUserResponseDto> success(UserEntity userEntity) {
        GetUserResponseDto responseBody = new GetUserResponseDto(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}