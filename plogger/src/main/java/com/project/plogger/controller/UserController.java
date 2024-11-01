package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.user.PatchPasswordRequestDto;
import com.project.plogger.dto.request.auth.TelAuthCheckRequestDto;
import com.project.plogger.dto.request.user.CommentRequestDto;
import com.project.plogger.dto.request.user.PatchTelAuthRequestDto;
import com.project.plogger.dto.request.user.PatchUserRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.admin.GetUserListResponseDto;
import com.project.plogger.service.AdminService;
import com.project.plogger.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1/mypage")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AdminService adminService;

    @PatchMapping(value = {"/", ""})
    public ResponseEntity<ResponseDto> patchUser(@RequestBody @Valid PatchUserRequestDto request, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = userService.patchUser(request, userId);
        return response;
    }
    
    @PatchMapping("/tel-auth")
    public ResponseEntity<ResponseDto> patchTelAuth(@RequestBody @Valid PatchTelAuthRequestDto request, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = userService.patchTelAuth(request, userId);
        return response;
    }

    @PatchMapping("/tel-auth-check")
    public ResponseEntity<ResponseDto> patchTelAuthCheck(@RequestBody @Valid TelAuthCheckRequestDto request, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = userService.patchTelAuthCheck(request, userId);
        return response;
    }

    @PatchMapping("/comment")
    public ResponseEntity<ResponseDto> patchComment(@RequestBody @Valid CommentRequestDto dto, @AuthenticationPrincipal String userId) {
        return userService.patchComment(dto, userId);
    }

    @PatchMapping("/update-password")
    public ResponseEntity<ResponseDto> patchPassword(@RequestBody @Valid PatchPasswordRequestDto request, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = userService.patchPassword(request, userId);
        return response;
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<? super GetUserListResponseDto> getUsers() {
        ResponseEntity<? super GetUserListResponseDto> response = adminService.getUserList();
        return response;
    }

}
