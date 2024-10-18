package com.project.plogger.service.Implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.gifticon.PostGifticonRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.entity.GifticonEntity;
import com.project.plogger.repository.GifticonRepository;
import com.project.plogger.service.GifticonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GifticonServiceImplement implements GifticonService{

    private final GifticonRepository gifticonRepository;

    @Override
    public ResponseEntity<ResponseDto> postGifticon(PostGifticonRequestDto dto) {

        try {

            GifticonEntity gifticonEntity = new GifticonEntity(dto);
            gifticonRepository.save(gifticonEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }
    
}
