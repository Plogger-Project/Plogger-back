package com.project.plogger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.plogger.dto.request.gifticon.PostGifticonRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.service.GifticonService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/gifticon")
@RequiredArgsConstructor
public class GifticonController {

    private final GifticonService gifticonService;

    @PostMapping(value = {"", "/"})
    public ResponseEntity<ResponseDto> postGifticon(
        @RequestBody @Valid PostGifticonRequestDto requestBody
    ){
        ResponseEntity<ResponseDto> response = gifticonService.postGifticon(requestBody);
        return response;
    };
    
}
