package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.admin.GetSignInResponseDto;
import com.project.plogger.dto.response.admin.GetUserListResponseDto;
import com.project.plogger.dto.response.admin.GetUserResponseDto;

public interface AdminService {

    ResponseEntity<? super GetUserListResponseDto> getUserList();

    ResponseEntity<? super GetSignInResponseDto> getSignIn(String userId);

    ResponseEntity<? super GetUserResponseDto> getUser(String userId);

    ResponseEntity<ResponseDto> deleteRecruitReport(Integer recruitId);

    ResponseEntity<ResponseDto> deleteActiveReport(Integer activeId);

    ResponseEntity<ResponseDto> deleteUser(String userId);
}
