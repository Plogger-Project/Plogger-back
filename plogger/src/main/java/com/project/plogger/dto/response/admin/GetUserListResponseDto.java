package com.project.plogger.dto.response.admin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.User;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.UserEntity;

import lombok.Getter;

@Getter
public class GetUserListResponseDto extends ResponseDto {
    
    private List<User> users;
    
    private GetUserListResponseDto(List<UserEntity> userEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.users = User.getList(userEntities);
    }

    public static ResponseEntity<GetUserListResponseDto> success(List<UserEntity> userEntities) {
        GetUserListResponseDto responseBody = new GetUserListResponseDto(userEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

}
