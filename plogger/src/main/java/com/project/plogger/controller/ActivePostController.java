package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.active.PatchActivePostRequestDto;
import com.project.plogger.dto.request.active.PostActivePostRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.service.active.ActivePostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/active")
@RequiredArgsConstructor
public class ActivePostController {

    private final ActivePostService activePostService;
    
    @PostMapping(value = {"", "/"})
    public ResponseEntity<ResponseDto> postActivePost(@RequestBody @Valid PostActivePostRequestDto dto, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = activePostService.postActivePost(dto, userId);
        return response;
    }

    @PatchMapping("/{activePostId}")
    public ResponseEntity<ResponseDto> patchActivePost(@RequestBody @Valid PatchActivePostRequestDto dto, @PathVariable Integer activePostId, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = activePostService.patchActivePost(dto, activePostId, userId);
        return response;
    }

    @DeleteMapping("/{activePostId}")
    public ResponseEntity<ResponseDto> deleteActivePost(@PathVariable Integer activePostId, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = activePostService.deleteActivePost(activePostId, userId);
        return response;
    }

}
