package com.project.plogger.service.Implement.active;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.plogger.dto.request.active.PatchActivePostRequestDto;
import com.project.plogger.dto.request.active.PostActivePostRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.active.GetActivePostResponseDto;
import com.project.plogger.dto.response.active.GetActivePostResultSet;
import com.project.plogger.entity.ActivePostEntity;
import com.project.plogger.repository.ActivePostRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.active.ActivePostService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivePostServiceImplement implements ActivePostService {

    private final UserRepository userRepository;
    private final ActivePostRepository activePostRepository;

    @Override
    public ResponseEntity<ResponseDto> postActivePost(PostActivePostRequestDto dto, String userId) {

        try {

            boolean isExistedUser = userRepository.existsById(userId);
            if (!isExistedUser) return ResponseDto.noExistUserId();

            ActivePostEntity activePostEntity = new ActivePostEntity(dto);
            activePostEntity.setActivePostWriterId(userId);
            activePostRepository.save(activePostEntity);

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }

    @Override
    public ResponseEntity<ResponseDto> patchActivePost(PatchActivePostRequestDto dto, Integer activePostId, String userId) {

        try {

            boolean isExistedUser = userRepository.existsById(userId);
            if (!isExistedUser) return ResponseDto.noExistUserId();

            boolean isExistedActivePost = activePostRepository.existsByActivePostId(activePostId);
            if (!isExistedActivePost) return ResponseDto.noExistActivePost();

            ActivePostEntity activePostEntity = activePostRepository.findByActivePostId(activePostId);

            String activePostWriter = activePostEntity.getActivePostWriterId();
            boolean isWriter = activePostWriter.equals(userId);
            if (!isWriter) return ResponseDto.noPermission();

            activePostEntity.patch(dto);
            activePostRepository.save(activePostEntity);

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDto> deleteActivePost(Integer activePostId, String userId) {
    
        try {

            boolean isExistedUser = userRepository.existsById(userId);
            if (!isExistedUser) return ResponseDto.noExistUserId();

            boolean isExistedActivePost = activePostRepository.existsByActivePostId(activePostId);
            if (!isExistedActivePost) return ResponseDto.noExistActivePost();

            ActivePostEntity activePostEntity = activePostRepository.findByActivePostId(activePostId);

            String activePostWriter = activePostEntity.getActivePostWriterId();
            boolean isWriter = activePostWriter.equals(userId);
            if (!isWriter) return ResponseDto.noPermission();

            activePostRepository.deleteByActivePostId(activePostId);

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }

    @Override
    public ResponseEntity<? super GetActivePostResponseDto> getActivePost(Integer activePostId) {

        GetActivePostResultSet resultSet = null;

        try {

            

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetActivePostResponseDto.success(null);
    }

}