package com.project.plogger.service.Implement;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.follow.PostFollowRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.follow.GetFolloweeListResponseDto;
import com.project.plogger.dto.response.follow.GetFollowerListResponseDto;
import com.project.plogger.entity.FollowEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.repository.FollowRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.FollowService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowServiceImplement implements FollowService{

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Override
    public ResponseEntity<ResponseDto> postFollow(String followerId, PostFollowRequestDto dto) {

        try {

            UserEntity followerEntity = userRepository.findByUserId(followerId);
            if(followerEntity == null) return ResponseDto.noExistUserId();
            
            UserEntity followeeEntity = userRepository.findByUserId(dto.getFolloweeId());
            if(followeeEntity == null) return ResponseDto.noExistUserId();

            FollowEntity followEntity = new FollowEntity(dto);
            followEntity.setFollowerId(followerId);

            followRepository.save(followEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
        
    }

    @Override
    public ResponseEntity<ResponseDto> deleteFollow(String followerId, String followeeId) {

        try {

            UserEntity followerEntity = userRepository.findByUserId(followerId);
            if(followerEntity == null) return ResponseDto.noExistUserId();
            
            UserEntity followeeEntity = userRepository.findByUserId(followeeId);
            if(followeeEntity == null) return ResponseDto.noExistUserId();

            FollowEntity followEntity = followRepository.findByFollowerIdAndFolloweeId(followerId, followeeId);
            if(followEntity == null) return ResponseDto.noExistFollow();

            followRepository.delete(followEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
        
    }

    @Override
    public ResponseEntity<? super GetFollowerListResponseDto> getFollowerList(String followeeId) {

        List<FollowEntity> followEntities = new ArrayList<>();

        try {

            followEntities = followRepository.findByFolloweeIdOrderByFollowIdDesc(followeeId);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetFollowerListResponseDto.success(followEntities);
        
    }

    @Override
    public ResponseEntity<? super GetFolloweeListResponseDto> getfolloweeList(String followerId) {

        List<FollowEntity> followEntities = new ArrayList<>();

        try {

            followEntities = followRepository.findByFollowerIdOrderByFollowIdDesc(followerId);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetFolloweeListResponseDto.success(followEntities);
        
    }
    
}
