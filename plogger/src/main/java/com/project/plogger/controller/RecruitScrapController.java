package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.scrap.GetRecruitScrapListResponseDto;
import com.project.plogger.dto.response.scrap.GetRecruitScrapResponseDto;
import com.project.plogger.service.RecruitScrapService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recruit/scrap")
@RequiredArgsConstructor
public class RecruitScrapController {
    
    private final RecruitScrapService scrapService;

    @PostMapping("/{recruitId}")
    public ResponseEntity<ResponseDto> recruitScrap(@PathVariable("recruitId") Integer recruitId, @AuthenticationPrincipal String userId) {
        ResponseEntity<ResponseDto> response = scrapService.recruitScrap(userId, recruitId);
        return response;
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<? super GetRecruitScrapListResponseDto> getRecruitScrapList() {
        ResponseEntity<? super GetRecruitScrapListResponseDto> response = scrapService.getScrapList();
        return response;
    }

    @GetMapping("/{recruitId}")
    public ResponseEntity<? super GetRecruitScrapResponseDto> getRecruitScrap(
        @AuthenticationPrincipal String userId,
        @PathVariable("recruitId") Integer recruitId
    ){
        ResponseEntity<? super GetRecruitScrapResponseDto> response = scrapService.getScrap(userId, recruitId);
        return response;
    };

}
