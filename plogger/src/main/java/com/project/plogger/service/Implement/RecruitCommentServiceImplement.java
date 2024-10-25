package com.project.plogger.service.Implement;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.recruit.PostRecruitCommentRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitCommentListResponseDto;
import com.project.plogger.entity.RecruitCommentEntity;
import com.project.plogger.entity.RecruitEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.repository.RecruitCommentRepository;
import com.project.plogger.repository.RecruitRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.RecruitCommentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitCommentServiceImplement implements RecruitCommentService{

    private final UserRepository userRepository;
    private final RecruitRepository recruitRepository;
    private final RecruitCommentRepository recruitCommentRepository;

    @Override
    public ResponseEntity<ResponseDto> postRecruitComment(PostRecruitCommentRequestDto dto, String userId, Integer recruitId) {

        try {

            RecruitCommentEntity recruitCommentEntity = new RecruitCommentEntity(dto);

            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();
            recruitCommentEntity.setRecruitCommentWriter(userId);

            RecruitEntity recruitEntity = recruitRepository.findByRecruitPostId(recruitId);
            if(recruitEntity == null) return ResponseDto.noExistRecruit();
            recruitCommentEntity.setRecruitId(recruitId);

            recruitCommentEntity.setRecruitCommentCreatedAt();

            recruitCommentRepository.save(recruitCommentEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
        
    }

    @Override
    public ResponseEntity<? super GetRecruitCommentListResponseDto> getRecruitCommentList(Integer recruitPostId) {

        List<RecruitCommentEntity> recruitCommentEntities = new ArrayList<>();

        try {

            recruitCommentEntities = recruitCommentRepository.findByRecruitIdOrderByRecruitCommentIdAsc(recruitPostId);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetRecruitCommentListResponseDto.success(recruitCommentEntities);
        
    }
    
}
