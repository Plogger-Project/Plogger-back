package com.project.plogger.service.Implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.entity.ActiveLikeEntity;
import com.project.plogger.entity.ActivePostEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.repository.ActiveLikeRepository;
import com.project.plogger.repository.ActivePostRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.ActiveLikeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActiveLikeServiceImplement implements ActiveLikeService {

    private final UserRepository userRepository;
    private final ActiveLikeRepository likeRepository;
    private final ActivePostRepository postRepository;

    @Override
    public ResponseEntity<ResponseDto> activeLike(String userId, Integer activeId) {

        try {

            ActivePostEntity activePostEntity = postRepository.findByActivePostId(activeId);
            if (activePostEntity == null) return ResponseDto.noExistActivePost();

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return ResponseDto.noExistUserId();

            boolean isLiked = likeRepository.existsByUserIdAndActiveId(userId, activeId);
            if (isLiked) {
                
                likeRepository.deleteByUserIdAndActiveId(userId, activeId); // 좋아요 취소
                activePostEntity.setActivePostLike(activePostEntity.getActivePostLike() - 1);
                postRepository.save(activePostEntity);
                return ResponseDto.likeClick();

            } else {

                ActiveLikeEntity likeEntity = new ActiveLikeEntity(userId, activeId); // 좋아요 삽입
                likeRepository.save(likeEntity); 
                activePostEntity.setActivePostLike(activePostEntity.getActivePostLike() + 1);
                postRepository.save(activePostEntity);
                return ResponseDto.likeUnclick();

            }

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

    }

}
