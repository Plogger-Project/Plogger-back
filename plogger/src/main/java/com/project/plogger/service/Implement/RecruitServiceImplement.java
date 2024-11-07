package com.project.plogger.service.Implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.recruit.PatchRecruitIsCompletedRequestDto;
import com.project.plogger.dto.request.recruit.PatchRecruitRequestDto;
import com.project.plogger.dto.request.recruit.PostRecruitRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitListResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitResponseDto;
import com.project.plogger.entity.RecruitEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.repository.RecruitRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.repository.resultset.GetRecruitResultSet;

import com.project.plogger.service.RecruitService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitServiceImplement implements RecruitService {
    
    private final RecruitRepository recruitRepository;
    private final UserRepository userRepository;
    
    @Override
    public ResponseEntity<ResponseDto> postRecruit(PostRecruitRequestDto dto, String userId) {
        try {

            RecruitEntity recruitEntity = new RecruitEntity(dto);

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null)
                return ResponseDto.noExistUserId();

            
            recruitEntity.setRecruitPostCreatedAt();
            recruitEntity.setRecruitPostWriter(userId);
            recruitRepository.save(recruitEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }
    @Override
    public ResponseEntity<? super GetRecruitResponseDto> getRecruit(Integer recruitPostId) {

        GetRecruitResultSet resultSet = null;

        try {
            
            resultSet = recruitRepository.getRecruit(recruitPostId);
            if (resultSet == null) {
                return ResponseDto.noExistRecruit();
            }

            RecruitEntity recruitEntity = recruitRepository.findByRecruitPostId(recruitPostId);
            if (recruitEntity == null) return ResponseDto.noExistRecruit();

            recruitEntity.setRecruitView(recruitEntity.getRecruitView() + 1);
            recruitRepository.save(recruitEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetRecruitResponseDto.success(resultSet);

    }
    @Override
    public ResponseEntity<? super GetRecruitListResponseDto> getRecruitList() {

        List<RecruitEntity> recruitEntities = new ArrayList<>();

        try {
            recruitEntities = recruitRepository.findByOrderByRecruitPostIdDesc();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetRecruitListResponseDto.success(recruitEntities);
    }

    @Override
    public ResponseEntity<ResponseDto> patchRecruit(Integer recruitPostId, String userId, PatchRecruitRequestDto dto) {

        try {
            RecruitEntity recruitEntity = recruitRepository.findByRecruitPostId(recruitPostId);
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (recruitEntity == null)return ResponseDto.noExistRecruit();
            if (userEntity == null)return ResponseDto.noExistUserId();

            if (!recruitEntity.getRecruitPostWriter().equals(userId)) {
                if (userEntity.getIsAdmin() != true) {
                    return ResponseDto.noPermission();
                } 
            }
            
            recruitEntity.RecruitPatch(dto);
            recruitRepository.save(recruitEntity);
            
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }
    
    @Override
    public ResponseEntity<ResponseDto> deleteRecruit(Integer recruitPostId, String userId) {

        try {
            RecruitEntity recruitEntity = recruitRepository.findByRecruitPostId(recruitPostId);
            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null)return ResponseDto.noExistUserId();
            if (recruitEntity == null)
                return ResponseDto.noExistRecruit();
            
            if (!recruitEntity.getRecruitPostWriter().equals(userId)) {
                if (userEntity.getIsAdmin() != true) {
                    return ResponseDto.noPermission();
                }
            }
            
            recruitRepository.delete(recruitEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }
    @Override
    public ResponseEntity<? super GetRecruitResponseDto> getProfileImage(Integer recruitPostId) {
        GetRecruitResultSet resultSet = null;

        try {
            resultSet = recruitRepository.getRecruit(recruitPostId);
            if(resultSet== null)
                return ResponseDto.noExistRecruit();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetRecruitResponseDto.success(resultSet);

    }
    @Override
    public ResponseEntity<ResponseDto> patchRecruitIsCompleted(Integer recruitPostId, String userId,
            PatchRecruitIsCompletedRequestDto dto) {
        try {
                
            RecruitEntity recruitEntity = recruitRepository.findByRecruitPostId(recruitPostId);
            if (recruitEntity == null) {
                return ResponseDto.noExistRecruit(); // 게시글이 없는 경우
            }
                if (!recruitEntity.getRecruitPostWriter().equals(userId)) {
                return ResponseDto.noPermission(); // 사용자 권한 없는 경우
            }
            recruitEntity.setIsCompleted(dto.getIsCompleted());
            recruitRepository.save(recruitEntity); // 변경된 상태 저장
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }
    
    

    
    
}
