package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import com.project.plogger.dto.response.admin.GetSignInResponseDto;
import com.project.plogger.dto.response.auth.SignInResponseDto;
import com.project.plogger.service.AuthService;
import com.project.plogger.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

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

    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody) {
        ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
        return response;
    }

    @PostMapping("/send-auth")
    public ResponseEntity<ResponseDto> sendAuthNumber(@RequestBody @Valid FindIdAuthCheckDto request) {
        ResponseEntity<ResponseDto> response = authService.sendAuthNumber(request);
        return response;
    }

    @PostMapping("/find-id")
    public ResponseEntity<? super FindIdResponseDto> findUserIdByTelNumber(@RequestBody @Valid FindIdRequestDto requestBody) {
        ResponseEntity<? super FindIdResponseDto> response = authService.findUserIdByTelNumber(requestBody);
        return response;
    }

    @PostMapping("/password-send-auth")
    public ResponseEntity<ResponseDto> sendPasswordAuthNumber (@RequestBody @Valid FindPasswordCheckDto request) {
        ResponseEntity<ResponseDto> response = authService.passwordAuthNumber(request);
        return response;
    }

    @PostMapping("/find-password")
    public ResponseEntity<? super FindPasswordResponseDto> findPasswordByTelNumber(@RequestBody @Valid FindPasswordRequestDto requestBody) {
        ResponseEntity<? super FindPasswordResponseDto> response = authService.findUserPasswordByTelNumber(requestBody);
        return response;
    }

    @GetMapping("/sign-in")
    public ResponseEntity<? super GetSignInResponseDto> getSignIn(
        @AuthenticationPrincipal String userId
    ){
        ResponseEntity<? super GetSignInResponseDto> response = userService.getSignIn(userId);
        return response;
    };
}
