package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.service.RecruitScrapService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recruit")
@RequiredArgsConstructor
public class RecruitScrapController {
    
    private final RecruitScrapService scrapService;

    @PostMapping("/scrap/{recruitId}")
    public ResponseEntity<ResponseDto> recruitScrap(@PathVariable Integer recruitId, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = scrapService.recruitScrap(userId, recruitId);
        return response;
    }

}
