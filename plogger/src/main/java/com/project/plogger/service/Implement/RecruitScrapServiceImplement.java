package com.project.plogger.service.Implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.entity.RecruitEntity;
import com.project.plogger.entity.RecruitScrapEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.repository.RecruitRepository;
import com.project.plogger.repository.RecruitScrapRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.RecruitScrapService;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RecruitScrapServiceImplement implements RecruitScrapService {

    private final UserRepository userRepository;
    private final RecruitRepository recruitRepository;
    private final RecruitScrapRepository scrapRepository;

    @Override
    public ResponseEntity<ResponseDto> recruitScrap(String userId, Integer recruitId) {

        try {

            RecruitEntity recruitEntity = recruitRepository.findByRecruitPostId(recruitId);
            if (recruitEntity == null)
                return ResponseDto.noExistRecruit();

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null)
                return ResponseDto.noExistUserId();

            boolean isScraped = scrapRepository.existsByUserIdAndRecruitId(userId, recruitId);
            if (isScraped) {

                scrapRepository.deleteByUserIdAndRecruitId(userId, recruitId); // 스크랩 취소
                return ResponseDto.scrapUnclick();

            } else {

                RecruitScrapEntity scrapEntity = new RecruitScrapEntity(userId, recruitId); // 스크랩 삽입
                scrapRepository.save(scrapEntity);
                return ResponseDto.scrapClick();

            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

    }

}
