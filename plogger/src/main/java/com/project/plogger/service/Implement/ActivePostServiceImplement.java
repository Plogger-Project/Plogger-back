package com.project.plogger.service.Implement;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.plogger.dto.request.active.PatchActivePostRequestDto;
import com.project.plogger.dto.request.active.PostActivePostRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.active.GetActivePostListResponseDto;
import com.project.plogger.dto.response.active.GetActivePostResponseDto;
import com.project.plogger.dto.response.active.GetMyRecruitPostListResponseDto;
import com.project.plogger.entity.ActivePostEntity;
import com.project.plogger.entity.ActiveTagEntity;
import com.project.plogger.entity.RecruitEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.repository.ActivePostRepository;
import com.project.plogger.repository.ActiveTagRepository;
import com.project.plogger.repository.RecruitJoinRepository;
import com.project.plogger.repository.RecruitRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.ActivePostService;
import com.project.plogger.service.MileageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivePostServiceImplement implements ActivePostService {

    private final UserRepository userRepository;
    private final ActiveTagRepository tagRepository;
    private final RecruitRepository recruitRepository;
    private final MileageService mileageService;
    private final RecruitJoinRepository joinRepository;
    private final ActivePostRepository activePostRepository;

    @Override
    public ResponseEntity<ResponseDto> postActivePost(PostActivePostRequestDto dto, String userId, Integer recruitId) {

        try {

            boolean isExistedUser = userRepository.existsById(userId);
            if (!isExistedUser)
                return ResponseDto.noExistUserId();

            ActivePostEntity activePostEntity = new ActivePostEntity(dto);
            activePostEntity.setActivePostWriterId(userId);
            activePostEntity.setRecruitId(recruitId);
            activePostRepository.save(activePostEntity);

            // 마일리지 지급 상태
            RecruitEntity recruitEntity = recruitRepository.findByRecruitPostId(recruitId);
            recruitEntity.setIsMileage(true);
            recruitRepository.save(recruitEntity);

            // 글 작성자에게 마일리지 지급
            mileageService.postUpMileage(userId, activePostEntity.getActivePostId());

            for (String tagId: dto.getActivePeople()) {
                ActiveTagEntity activeTagEntity = new ActiveTagEntity(tagId, activePostEntity.getActivePostId(), recruitEntity.getRecruitPostId());
                tagRepository.save(activeTagEntity);

                mileageService.postUpMileage(tagId, activePostEntity.getActivePostId());
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }

    @Override
    public ResponseEntity<ResponseDto> patchActivePost(PatchActivePostRequestDto dto, Integer activePostId,
            String userId) {

        try {

            boolean isExistedUser = userRepository.existsById(userId);
            if (!isExistedUser)
                return ResponseDto.noExistUserId();

            boolean isExistedActivePost = activePostRepository.existsByActivePostId(activePostId);
            if (!isExistedActivePost)
                return ResponseDto.noExistActivePost();

            ActivePostEntity activePostEntity = activePostRepository.findByActivePostId(activePostId);
            UserEntity userEntity = userRepository.findByUserId(userId);
            String activePostWriter = activePostEntity.getActivePostWriterId();
            boolean isWriter = activePostWriter.equals(userId);
            if (!isWriter) {
                if (userEntity.getIsAdmin() != true) {
                    return ResponseDto.noPermission();
                }
            }

            activePostEntity.patch(dto);
            activePostRepository.save(activePostEntity);

        } catch (Exception exception) {
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
            if (!isExistedUser)
                return ResponseDto.noExistUserId();

            boolean isExistedActivePost = activePostRepository.existsByActivePostId(activePostId);
            if (!isExistedActivePost)
                return ResponseDto.noExistActivePost();

            ActivePostEntity activePostEntity = activePostRepository.findByActivePostId(activePostId);
            UserEntity userEntity = userRepository.findByUserId(userId);
            String activePostWriter = activePostEntity.getActivePostWriterId();
            boolean isWriter = activePostWriter.equals(userId);
            if (!isWriter) {
                if (userEntity.getIsAdmin() != true) {
                    return ResponseDto.noPermission();
                }
            }
                

            activePostRepository.deleteByActivePostId(activePostId);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }

    @Override
    public ResponseEntity<? super GetActivePostResponseDto> getActivePost(Integer activePostId) {

        ActivePostEntity activePostEntity = null;
        List<String> activePeople = new ArrayList<>();

        try {

            activePostEntity = activePostRepository.findByActivePostId(activePostId);
            if (activePostEntity == null)
                return ResponseDto.noExistActivePost();

            activePostEntity.setActiveView(activePostEntity.getActiveView() + 1);
            activePostRepository.save(activePostEntity);

            List<ActiveTagEntity> activeTagEntities = tagRepository.findByActiveId(activePostId);

            for (ActiveTagEntity activeTagEntity : activeTagEntities) {
                activePeople.add(activeTagEntity.getUserId());
            }

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetActivePostResponseDto.success(activePostEntity, activePeople);
    }

    @Override
    public ResponseEntity<? super GetActivePostListResponseDto> getActivePostList() {

        List<ActivePostEntity> activePostEntities = new ArrayList<>();

        try {

            activePostEntities = activePostRepository.findAllByOrderByActivePostIdDesc();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetActivePostListResponseDto.success(activePostEntities);

    }

    @Override
    public ResponseEntity<? super GetMyRecruitPostListResponseDto> getMyRecruitPosts(String userId) {

        List<RecruitEntity> recruitEntities = new ArrayList<>();

        try {

            boolean isExistedUser = userRepository.existsById(userId);
            if (!isExistedUser) return ResponseDto.noExistUserId();

            recruitEntities = recruitRepository.findByRecruitPostWriterAndIsCompletedTrueAndIsMileageFalse(userId);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetMyRecruitPostListResponseDto.success(recruitEntities, joinRepository);

    }

}