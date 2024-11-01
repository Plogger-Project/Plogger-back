package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.response.mileage.GetMileageListResponseDto;
import com.project.plogger.service.MileageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/mileage")
@RequiredArgsConstructor
public class MileageController {

    private final MileageService mileageService;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<? super GetMileageListResponseDto> getSignInMileageList(
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super GetMileageListResponseDto> response = mileageService.getMileageList(userId);
        return response;
    }
    
}
