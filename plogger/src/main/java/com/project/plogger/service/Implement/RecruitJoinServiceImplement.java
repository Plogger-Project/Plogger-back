package com.project.plogger.service.Implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.entity.RecruitEntity;
import com.project.plogger.entity.RecruitJoinEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.repository.RecruitJoinRepository;
import com.project.plogger.repository.RecruitRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.RecruitJoinService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitJoinServiceImplement implements RecruitJoinService {

    private final UserRepository userRepository;
    private final RecruitRepository recruitRepository;
    private final RecruitJoinRepository joinRepository;

    @Transactional
    @Override
    public ResponseEntity<ResponseDto> recruitJoin(String userId, Integer recruitId) {
        
        try {

            RecruitEntity recruitEntity = recruitRepository.findByRecruitPostId(recruitId);
            if (recruitEntity == null) return ResponseDto.noExistRecruit();

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return ResponseDto.noExistUserId();

            if (recruitEntity.getIsCompleted()) {
                return ResponseDto.fullPeople();
            }

            if (recruitEntity.getRecruitPostWriter().equals(userId)) { // 자신이 작성한 글에는 참여 못함
                return ResponseDto.noSelfParticipation();
            }

            boolean isJoined = joinRepository.existsByUserIdAndRecruitId(userId, recruitId);
            if (isJoined) {

                joinRepository.deleteByUserIdAndRecruitId(userId, recruitId);
                recruitEntity.setCurrentPeople(recruitEntity.getCurrentPeople() - 1);
                recruitRepository.save(recruitEntity);

            } else {

                RecruitJoinEntity joinEntity = new RecruitJoinEntity(userId, recruitId);
                joinRepository.save(joinEntity);
                recruitEntity.setCurrentPeople(recruitEntity.getCurrentPeople() + 1);
                recruitRepository.save(recruitEntity);

            }
            
            recruitRepository.save(recruitEntity);
            return ResponseDto.success();

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

    }
    
}
