package com.project.plogger.service.Implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.project.plogger.dto.request.scrap.PostRecruitScrapRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.entity.RecruitScrapEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.repository.RecruitScrapRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.RecruitScrapService;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RecruitScrapServiceImplement implements RecruitScrapService {

    private final UserRepository userRepository;
    // recruitrepository 가져오기
    private final RecruitScrapRepository scrapRepository;

    @Override
    public ResponseEntity<ResponseDto> postRecruitScrap(PostRecruitScrapRequestDto dto, String userId, String recruitId) {

        try {

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return ResponseDto.noExistUserId();

            RecruitScrapEntity scrapEntity = scrapRepository.findByUserIdAndRecruitId(userId, recruitId);
            if (scrapEntity == null) return null; // ResponseDto 작성해야함
            // recruitrepository 에서 recruitId 가져오기

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }
    
}
