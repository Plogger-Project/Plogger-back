package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.auth.FindIdAuthCheckDto;
import com.project.plogger.dto.request.auth.FindIdRequestDto;
import com.project.plogger.dto.request.auth.FindPasswordCheckDto;
import com.project.plogger.dto.request.auth.FindPasswordRequestDto;
import com.project.plogger.dto.request.auth.IdCheckRequestDto;
import com.project.plogger.dto.request.auth.SignInRequestDto;
import com.project.plogger.dto.request.auth.SignUpRequestDto;
import com.project.plogger.dto.request.auth.TelAuthCheckRequestDto;
import com.project.plogger.dto.request.auth.TelAuthRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.auth.FindIdResponseDto;
import com.project.plogger.dto.response.auth.FindPasswordResponseDto;
import com.project.plogger.dto.response.auth.SignInResponseDto;

public interface AuthService {
    
    ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto); 
    ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto dto);
    ResponseEntity<ResponseDto> telAuth(TelAuthRequestDto dto);
    ResponseEntity<ResponseDto> telAuthCheck(TelAuthCheckRequestDto dto);
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
    ResponseEntity<ResponseDto> sendAuthNumber(FindIdAuthCheckDto dto);
    ResponseEntity<? super FindIdResponseDto> findUserIdByTelNumber(FindIdRequestDto dto);
    ResponseEntity<? super FindPasswordResponseDto> findUserPasswordByTelNumber(FindPasswordRequestDto dto);
    ResponseEntity<ResponseDto> passwordAuthNumber(FindPasswordCheckDto dto);
}
