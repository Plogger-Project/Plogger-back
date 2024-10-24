package com.project.plogger.service;

import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.request.gifticon.PatchGifticonRequestDto;
import com.project.plogger.dto.request.gifticon.PostGifticonRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.gifticon.GetGifticonListResponseDto;
import com.project.plogger.dto.response.gifticon.GetGifticonResponseDto;

public interface GifticonService {

    ResponseEntity<ResponseDto> postGifticon(PostGifticonRequestDto dto);
    ResponseEntity<? super GetGifticonListResponseDto> getGifticonList();
    ResponseEntity<? super GetGifticonResponseDto> getGifticon(Integer gifticonId);
    ResponseEntity<ResponseDto> patchGifticon(Integer gifticonId, PatchGifticonRequestDto dto);  
    ResponseEntity<ResponseDto> deleteGifticon(Integer gifticonId); 
}
