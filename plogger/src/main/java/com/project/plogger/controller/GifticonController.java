package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.gifticon.PatchGifticonRequestDto;
import com.project.plogger.dto.request.gifticon.PostGifticonRequestDto;
import com.project.plogger.dto.request.mileage.PostMileageDownRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.gifticon.GetGifticonListResponseDto;
import com.project.plogger.dto.response.gifticon.GetGifticonResponseDto;
import com.project.plogger.service.GifticonService;
import com.project.plogger.service.MileageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/gifticon")
@RequiredArgsConstructor
public class GifticonController {

    private final GifticonService gifticonService;
    private final MileageService mileageService;
    

    @PostMapping(value = {"", "/"})
    public ResponseEntity<ResponseDto> postGifticon(
        @AuthenticationPrincipal String userId,
        @RequestBody @Valid PostGifticonRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> response = gifticonService.postGifticon(userId, requestBody);
        return response;
    };

    @GetMapping(value = {"", "/"})
    public ResponseEntity<? super GetGifticonListResponseDto> getGifticonList() {
        ResponseEntity<? super GetGifticonListResponseDto> response = gifticonService.getGifticonList();
        return response;
    }

    @GetMapping("/{gifticonId}")
    public ResponseEntity<? super GetGifticonResponseDto> getGifticon(
        @PathVariable("gifticonId") Integer gifticonId
    ){
        ResponseEntity<? super GetGifticonResponseDto> response = gifticonService.getGifticon(gifticonId);
        return response;
    };

    @PatchMapping("/{gifticonId}")
    public ResponseEntity<ResponseDto> patchGifticon(
        @AuthenticationPrincipal String userId,
        @PathVariable("gifticonId") Integer gifticonId,
        @RequestBody @Valid PatchGifticonRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> response = gifticonService.patchGifticon(userId, gifticonId, requestBody);
        return response;
    };

    @DeleteMapping("/{gifticonId}")
    public ResponseEntity<ResponseDto> deleteGifticon(
        @AuthenticationPrincipal String userId,
        @PathVariable("gifticonId") Integer gifticonId
    ){
        ResponseEntity<ResponseDto> response = gifticonService.deleteGifticon(userId, gifticonId);
        return response;
    };

    @PostMapping("/{gifticonId}")
    public ResponseEntity<ResponseDto> purchaseGifticon(
        @AuthenticationPrincipal String userId,
        @RequestBody @Valid PostMileageDownRequestDto requestBody,
        @PathVariable("gifticonId") Integer gifticonId
    ){
        ResponseEntity<ResponseDto> response = mileageService.postMileage(requestBody, userId, gifticonId);
        return response;
    };
    
}
