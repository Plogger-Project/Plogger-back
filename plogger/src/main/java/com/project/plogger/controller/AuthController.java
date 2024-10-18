package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.auth.IdCheckRequestDto;
import com.project.plogger.dto.request.auth.SignUpRequestDto;
import com.project.plogger.dto.request.auth.TelAuthCheckRequestDto;
import com.project.plogger.dto.request.auth.TelAuthRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/id-check")
    public ResponseEntity<ResponseDto> idCheck(@RequestBody @Valid IdCheckRequestDto request) {
        ResponseEntity<ResponseDto> response = authService.idCheck(request);
        return response;
    } 

    @PostMapping("/tel-auth")
    public ResponseEntity<ResponseDto> telAuth(@RequestBody @Valid TelAuthRequestDto request) {
        ResponseEntity<ResponseDto> response = authService.telAuth(request);
        return response;
    }

    @PostMapping("/tel-auth-check")
    public ResponseEntity<ResponseDto> telAuthCheck(@RequestBody @Valid TelAuthCheckRequestDto request) {
        ResponseEntity<ResponseDto> response = authService.telAuthCheck(request);
        return response;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto> SignUp(@RequestBody @Valid SignUpRequestDto request) {
        ResponseEntity<ResponseDto> response = authService.signUp(request);
        return response;
    }

}
