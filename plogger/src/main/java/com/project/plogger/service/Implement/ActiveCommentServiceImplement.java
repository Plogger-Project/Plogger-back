package com.project.plogger.service.Implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.active.PatchActiveCommentRequestDto;
import com.project.plogger.dto.request.active.PostActiveCommentRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.active.GetActiveCommentListResponseDto;
import com.project.plogger.entity.ActiveCommentEntity;
import com.project.plogger.entity.ActivePostEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.repository.ActiveCommentRepository;
import com.project.plogger.repository.ActivePostRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.ActiveCommentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActiveCommentServiceImplement implements ActiveCommentService{

    private final UserRepository userRepository;
    private final ActivePostRepository activePostRepository;
    private final ActiveCommentRepository activeCommentRepository;

    @Override
    public ResponseEntity<ResponseDto> postActiveComment(PostActiveCommentRequestDto dto, String userId, Integer activeId) {

        try {

            ActiveCommentEntity activeCommentEntity = new ActiveCommentEntity(dto);

            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();
            activeCommentEntity.setActiveCommentWriter(userId);

            ActivePostEntity activePostEntity = activePostRepository.findByActivePostId(activeId);
            if(activePostEntity == null) return ResponseDto.noExistActivePost();
            activeCommentEntity.setActiveId(activeId);

            activeCommentEntity.setActiveCommentCreatedAt();

            activeCommentRepository.save(activeCommentEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
        
    }

	@Override
	public ResponseEntity<? super GetActiveCommentListResponseDto> getActiveCommentList(Integer activeId) {

        List<ActiveCommentEntity> activeCommentEntities = new ArrayList<>();

        try {

            boolean isExistedActivePost = activePostRepository.existsByActivePostId(activeId);
            if (!isExistedActivePost) return ResponseDto.noExistActivePost();

            activeCommentEntities = activeCommentRepository.findByActiveIdOrderByActiveCommentIdAsc(activeId);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetActiveCommentListResponseDto.success(activeCommentEntities);
		
	}

    @Override
    public ResponseEntity<ResponseDto> patchActiveComment(Integer activeId, Integer activeCommentId, String userId, PatchActiveCommentRequestDto dto) {

        try {

            ActiveCommentEntity activeCommentEntity = activeCommentRepository.findByActiveCommentId(activeCommentId);
            UserEntity userEntity = userRepository.findByUserId(userId);
            if(activeCommentEntity == null) return ResponseDto.noExistActiveComment();

            if(!activeCommentEntity.getActiveId().equals(activeId)) return ResponseDto.noExistActivePost();
            if (!activeCommentEntity.getActiveCommentWriter().equals(userId)) {
                if (userEntity.getIsAdmin() != true) {
                    return ResponseDto.noPermission();
                }
            }
            

            activeCommentEntity.patch(dto);
            activeCommentEntity.setActiveCommentCreatedAt();

            activeCommentRepository.save(activeCommentEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
        
    }

    @Override
    public ResponseEntity<ResponseDto> deleteActiveComment(Integer activeId, Integer activeCommentId, String userId) {

        try {

            ActiveCommentEntity activeCommentEntity = activeCommentRepository.findByActiveCommentId(activeCommentId);
            UserEntity userEntity = userRepository.findByUserId(userId);
            if(activeCommentEntity == null) return ResponseDto.noExistActiveComment();

            if(!activeCommentEntity.getActiveId().equals(activeId)) return ResponseDto.noExistActivePost();
            if (!activeCommentEntity.getActiveCommentWriter().equals(userId)) {

                if (userEntity.getIsAdmin() != true) {
                    return ResponseDto.noPermission();
                }
            }
            activeCommentRepository.delete(activeCommentEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
        
    }
    
}
