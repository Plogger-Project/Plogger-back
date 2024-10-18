package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.auth.IdCheckRequestDto;
import com.project.plogger.dto.request.auth.SignUpRequestDto;
import com.project.plogger.dto.request.auth.TelAuthCheckRequestDto;
import com.project.plogger.dto.request.auth.TelAuthRequestDto;
import com.project.plogger.dto.response.ResponseDto;

public interface AuthService {
    
    ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto); 
    ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto dto);
    ResponseEntity<ResponseDto> telAuth(TelAuthRequestDto dto);
    ResponseEntity<ResponseDto> telAuthCheck(TelAuthCheckRequestDto dto);

}
