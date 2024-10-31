package com.project.plogger.service.Implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.active.PostActiveTagRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.entity.ActivePostEntity;
import com.project.plogger.entity.ActiveTagEntity;
import com.project.plogger.repository.ActivePostRepository;
import com.project.plogger.repository.ActiveTagRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.ActiveTagService;
import com.project.plogger.service.MileageService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActiveTagServiceImplement implements ActiveTagService {
    
    private final MileageService mileageService;
    private final UserRepository userRepository;
    private final ActiveTagRepository tagRepository;
    private final ActivePostRepository activeRepository;

    @Override
    public ResponseEntity<ResponseDto> postTag(PostActiveTagRequestDto dto, Integer activeId, Integer recruitId) {

        String tagId = dto.getTagId();

        try {

            ActivePostEntity activePostEntity = activeRepository.findByActivePostId(activeId);

            if (activePostEntity.getActivePostWriterId().equals(tagId)) return ResponseDto.noSelfTag();

            boolean isExistedUser = userRepository.existsByUserId(tagId);
            if (!isExistedUser) return ResponseDto.noExistUserId();

            boolean isExistedActivePost = activeRepository.existsByActivePostId(activeId);
            if (!isExistedActivePost) return ResponseDto.noExistActivePost();

            ActiveTagEntity tagEntity = new ActiveTagEntity(tagId, activeId, recruitId);
            tagRepository.save(tagEntity);

            // 추가로 태그된 유저 마일리지 지급
            mileageService.postUpMileage(tagId, activeId);

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDto> deleteTag(PostActiveTagRequestDto dto, Integer activeId, Integer recruitId) {

        String tagId = dto.getTagId();
        
        try {

            boolean isExistedUser = userRepository.existsByUserId(tagId);
            if (!isExistedUser) return ResponseDto.noExistUserId();

            boolean isExistedActivePost = activeRepository.existsByActivePostId(activeId);
            if (!isExistedActivePost) return ResponseDto.noExistActivePost();

            ActiveTagEntity tagEntity = tagRepository.findByUserIdAndActiveId(tagId, activeId);
            if (tagEntity == null) return ResponseDto.noExistActiveTag();

            tagRepository.delete(tagEntity);

            // 태그가 삭제된 유저 마일리지 회수
            mileageService.postTagRemoveMileage(tagId, activeId);

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }
    
}