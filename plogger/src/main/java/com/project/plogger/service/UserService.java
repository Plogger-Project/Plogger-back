package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.auth.TelAuthCheckRequestDto;
import com.project.plogger.dto.request.user.PatchTelAuthRequestDto;
import com.project.plogger.dto.request.user.PatchUserRequestDto;
import com.project.plogger.dto.response.ResponseDto;

public interface UserService {

    ResponseEntity<ResponseDto> patchTelAuth(PatchTelAuthRequestDto dto, String userId);
    ResponseEntity<ResponseDto> patchTelAuthCheck(TelAuthCheckRequestDto dto, String userId);
    ResponseEntity<ResponseDto> patchUser(PatchUserRequestDto dto, String userId);
}
