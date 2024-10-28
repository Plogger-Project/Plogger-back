package com.project.plogger.service.Implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.mileage.PostMileageDownRequestDto;
import com.project.plogger.dto.request.mileage.PostMileageUpRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.entity.ActivePostEntity;
import com.project.plogger.entity.GifticonEntity;
import com.project.plogger.entity.MileageEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.repository.ActivePostRepository;
import com.project.plogger.repository.GifticonRepository;
import com.project.plogger.repository.MileageRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.MileageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MileageServiceImplement implements MileageService{

    private final UserRepository userRepository;
    private final GifticonRepository gifticonRepository;
    private final ActivePostRepository activePostRepository;
    private final MileageRepository mileageRepository;

    @Override
    public ResponseEntity<ResponseDto> postMileage(PostMileageUpRequestDto dto, String userId, Integer activeId) {

        try {

            MileageEntity mileageEntity = new MileageEntity(dto);

            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();
            mileageEntity.setUserId(userId);
            
            ActivePostEntity activePostEntity = activePostRepository.findByActivePostId(activeId);
            if(activePostEntity == null) return ResponseDto.noExistActivePost();
            mileageEntity.setActiveId(activeId);

            userEntity.upMileage();
            mileageEntity.setMileageResult(userEntity.getMileage());

            mileageRepository.save(mileageEntity);
            userRepository.save(userEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
        
    }

    @Override
    public ResponseEntity<ResponseDto> postMileage(PostMileageDownRequestDto dto, String userId, Integer gifticonId) {

        try {

            MileageEntity mileageEntity = new MileageEntity(dto);

            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();
            mileageEntity.setUserId(userId);
            
            GifticonEntity gifticonEntity = gifticonRepository.findByGifticonId(gifticonId);
            if(gifticonEntity == null) return ResponseDto.noExistGifticon();
            mileageEntity.setGifticonId(gifticonId);

            mileageEntity.setMileageChange(gifticonEntity.getMileageCost());

            userEntity.downMileage(gifticonEntity.getMileageCost());
            mileageEntity.setMileageResult(userEntity.getMileage());

            mileageRepository.save(mileageEntity);
            userRepository.save(userEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
        
    }
    
}
