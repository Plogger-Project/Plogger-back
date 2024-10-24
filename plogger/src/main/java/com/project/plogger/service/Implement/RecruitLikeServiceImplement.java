package com.project.plogger.service.Implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.entity.RecruitEntity;
import com.project.plogger.entity.RecruitLikeEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.repository.RecruitLikeRepository;
import com.project.plogger.repository.RecruitRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.RecruitLikeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitLikeServiceImplement implements RecruitLikeService {

    private final UserRepository userRepository;
    private final RecruitLikeRepository likeRepository;
    private final RecruitRepository recruitRepository;

    @Override
    public ResponseEntity<ResponseDto> recruitLike(String userId, Integer recruitId) {

        try {

            RecruitEntity recruitEntity = recruitRepository.findByRecruitPostId(recruitId);
            if (recruitEntity == null) return ResponseDto.noExistRecruit();

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return ResponseDto.noExistUserId();

            boolean isLiked = likeRepository.existsByUserIdAndRecruitId(userId, recruitId);
            if (isLiked) {
                
                likeRepository.deleteByUserIdAndRecruitId(userId, recruitId); // 좋아요 취소
                recruitEntity.setRecruitPostLike(recruitEntity.getRecruitPostLike() - 1);
                recruitRepository.save(recruitEntity);
                return ResponseDto.likeClick();

            } else {

                RecruitLikeEntity likeEntity = new RecruitLikeEntity(userId, recruitId); // 좋아요 삽입
                likeRepository.save(likeEntity); 
                recruitEntity.setRecruitPostLike(recruitEntity.getRecruitPostLike() + 1);
                recruitRepository.save(recruitEntity);
                return ResponseDto.likeUnclick();

            }

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

    }

}
