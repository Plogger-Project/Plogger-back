package com.project.plogger.dto.response.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.UserEntity;

import lombok.Getter;

@Getter
public class GetSignInResponseDto extends ResponseDto{
    
    private String userId;
    private String name;
    private String password;
    private String telNumber;
    private String address;
    private String profileImage;
    private Boolean isAdmin;
    private Integer ecoScore;
    private Integer mileage;
    private String comment;

    public GetSignInResponseDto(UserEntity userEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.userId = userEntity.getUserId();
        this.name = userEntity.getName();
        this.password = userEntity.getPassword();
        this.telNumber = userEntity.getTelNumber();
        this.address = userEntity.getAddress();
        this.isAdmin = userEntity.getIsAdmin();
        this.ecoScore = userEntity.getEcoScore();
        this.mileage = userEntity.getMileage();
        this.comment = userEntity.getComment();
    }

    public static ResponseEntity<GetSignInResponseDto> success(UserEntity userEntity) {
        GetSignInResponseDto responseBody = new GetSignInResponseDto(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
