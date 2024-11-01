package com.project.plogger.service.Implement;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.MileageDownDto;
import com.project.plogger.dto.MileageTagRemoveDto;
import com.project.plogger.dto.MileageUpDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.mileage.GetMileageListResponseDto;
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
    public ResponseEntity<ResponseDto> postUpMileage(String userId, Integer activeId) {

        try {

            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();
            
            ActivePostEntity activePostEntity = activePostRepository.findByActivePostId(activeId);
            if(activePostEntity == null) return ResponseDto.noExistActivePost();

            userEntity.upMileage();

            MileageUpDto dto = new MileageUpDto(userId, activeId, userEntity.getMileage());
            MileageEntity mileageEntity = new MileageEntity(dto);

            mileageRepository.save(mileageEntity);
            userRepository.save(userEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
        
    }

    @Override
    public ResponseEntity<ResponseDto> postTagRemoveMileage(String userId, Integer activeId) {

        try {

            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();
            
            ActivePostEntity activePostEntity = activePostRepository.findByActivePostId(activeId);
            if(activePostEntity == null) return ResponseDto.noExistActivePost();

            userEntity.downMileage();

            MileageTagRemoveDto dto = new MileageTagRemoveDto(userId, activeId, userEntity.getMileage());
            MileageEntity mileageEntity = new MileageEntity(dto);

            mileageRepository.save(mileageEntity);
            userRepository.save(userEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }

    @Override
    public ResponseEntity<ResponseDto> postDownMileage(String userId, Integer gifticonId) {

        try {

            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();
            
            GifticonEntity gifticonEntity = gifticonRepository.findByGifticonId(gifticonId);
            if(gifticonEntity == null) return ResponseDto.noExistGifticon();

            userEntity.downMileage(gifticonEntity.getMileageCost());

            MileageDownDto dto = new MileageDownDto(userId, gifticonId, userEntity.getMileage());
            MileageEntity mileageEntity = new MileageEntity(dto, gifticonEntity.getMileageCost());

            mileageRepository.save(mileageEntity);
            userRepository.save(userEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
        
    }

    @Override
    public ResponseEntity<? super GetMileageListResponseDto> getMileageList(String userId) {

        List<MileageEntity> mileageEntities = new ArrayList<>();

        try {

            mileageEntities = mileageRepository.findByUserIdOrderByMileageIdDesc(userId);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetMileageListResponseDto.success(mileageEntities);
        
    }
    
}
