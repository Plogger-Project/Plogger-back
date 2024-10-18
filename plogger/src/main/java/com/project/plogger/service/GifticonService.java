package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.gifticon.PostGifticonRequestDto;
import com.project.plogger.dto.response.ResponseDto;

public interface GifticonService {

    ResponseEntity<ResponseDto> postGifticon(PostGifticonRequestDto dto);
    
}
