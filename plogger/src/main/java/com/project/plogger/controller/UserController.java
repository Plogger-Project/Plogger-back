package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.auth.TelAuthCheckRequestDto;
import com.project.plogger.dto.request.user.PatchTelAuthRequestDto;
import com.project.plogger.dto.request.user.PatchUserRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/mypage")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // @AuthenticationPrincipal String userId, @PatchMapping(value = {"/", ""})
    @PatchMapping("/{userId}")
    public ResponseEntity<ResponseDto> patchUser(@RequestBody @Valid PatchUserRequestDto request, @PathVariable String userId) {
        ResponseEntity<ResponseDto> response = userService.patchUser(request, userId);
        return response;
    }
    
    @PatchMapping("/tel-auth")
    public ResponseEntity<ResponseDto> patchTelAuth(@RequestBody @Valid PatchTelAuthRequestDto request, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = userService.patchTelAuth(request, userId);
        return response;
    }

    @PostMapping("/tel-auth-check")
    public ResponseEntity<ResponseDto> patchTelAuthCheck(@RequestBody @Valid TelAuthCheckRequestDto request, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = userService.patchTelAuthCheck(request, userId);
        return response;
    }

}
