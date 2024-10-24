package com.project.plogger.service.Implement;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.gifticon.PatchGifticonRequestDto;
import com.project.plogger.dto.request.gifticon.PostGifticonRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.gifticon.GetGifticonListResponseDto;
import com.project.plogger.dto.response.gifticon.GetGifticonResponseDto;
import com.project.plogger.entity.GifticonEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.repository.GifticonRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.GifticonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GifticonServiceImplement implements GifticonService{

    private final UserRepository userRepository;
    private final GifticonRepository gifticonRepository;

    @Override
    public ResponseEntity<ResponseDto> postGifticon(String userId, PostGifticonRequestDto dto) {

        try {

            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();
            if(!userEntity.getIsAdmin()) return ResponseDto.noPermission();

            GifticonEntity gifticonEntity = new GifticonEntity(dto);
            gifticonRepository.save(gifticonEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetGifticonListResponseDto> getGifticonList() {

        List<GifticonEntity> gifticonEntities = new ArrayList<>();

        try {

            gifticonEntities = gifticonRepository.findByOrderByGifticonIdDesc();
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetGifticonListResponseDto.success(gifticonEntities);
        
    }

    @Override
    public ResponseEntity<? super GetGifticonResponseDto> getGifticon(Integer gifticonId) {

        GifticonEntity gifticonEntity = null;

        try {

            gifticonEntity = gifticonRepository.findByGifticonId(gifticonId);
            if(gifticonEntity == null) return ResponseDto.noExistGifticon();
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetGifticonResponseDto.success(gifticonEntity);

    }

    @Override
    public ResponseEntity<ResponseDto> patchGifticon(String userId, Integer gifticonId, PatchGifticonRequestDto dto) {

        try {

            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();
            if(!userEntity.getIsAdmin()) return ResponseDto.noPermission();

            GifticonEntity gifticonEntity = gifticonRepository.findByGifticonId(gifticonId);
            if(gifticonEntity == null) return ResponseDto.noExistGifticon();

            gifticonEntity.patch(dto);
            gifticonRepository.save(gifticonEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }

    @Override
    public ResponseEntity<ResponseDto> deleteGifticon(String userId, Integer gifticonId) {

        try {

            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();
            if(!userEntity.getIsAdmin()) return ResponseDto.noPermission();

            GifticonEntity gifticonEntity = gifticonRepository.findByGifticonId(gifticonId);
            if(gifticonEntity == null) return ResponseDto.noExistGifticon();

            gifticonRepository.delete(gifticonEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }
    
}
