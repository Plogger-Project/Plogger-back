package com.project.plogger.service.Implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.qna.PatchQnARequestDto;
import com.project.plogger.dto.request.qna.PostQnARequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.qna.GetQnAListResponseDto;
import com.project.plogger.dto.response.qna.GetQnAResponseDto;
import com.project.plogger.entity.QnAEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.repository.QnARepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.QnAService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnAServiceImplement implements QnAService{

    private final QnARepository qnaRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseDto> postQnA(PostQnARequestDto dto, String userId) {

        try {

            QnAEntity qnaEntity = new QnAEntity(dto);

            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();
            qnaEntity.setQnaPostWriter(userId);
            qnaEntity.setQnaPostCreatedAt();

            qnaRepository.save(qnaEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetQnAListResponseDto> getQnAList() {

        List<QnAEntity> qnaEntities = new ArrayList<>();

        try {

            qnaEntities = qnaRepository.findByOrderByQnaPostIdDesc();
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetQnAListResponseDto.success(qnaEntities);

    }

    @Override
    public ResponseEntity<? super GetQnAResponseDto> getQna(Integer qnaPostId) {

        QnAEntity qnaEntity = null;

        try {

            qnaEntity = qnaRepository.findByQnaPostId(qnaPostId);
            if(qnaEntity == null) return ResponseDto.noExistQnA();
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetQnAResponseDto.success(qnaEntity);
    }

    @Override
    public ResponseEntity<ResponseDto> patchQnA(Integer qnaPostId, String userId, PatchQnARequestDto dto) {

        try {

            QnAEntity qnaEntity = qnaRepository.findByQnaPostId(qnaPostId);
            UserEntity userEntity = userRepository.findByUserId(userId);
            if(qnaEntity == null) return ResponseDto.noExistQnA();
            
            if (!qnaEntity.getQnaPostWriter().equals(userId)) {
                if (userEntity.getIsAdmin() != true) {
                    return ResponseDto.noPermission();
                }
            }
            qnaEntity.patch(dto);
            qnaEntity.setQnaPostCreatedAt();
            
            qnaRepository.save(qnaEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> deleteQnA(Integer qnaPostId, String userId) {

        try {

            QnAEntity qnaEntity = qnaRepository.findByQnaPostId(qnaPostId);
            UserEntity userEntity = userRepository.findByUserId(userId);
            if(qnaEntity == null) return ResponseDto.noExistQnA();

            if (!qnaEntity.getQnaPostWriter().equals(userId)) {
                if (userEntity.getIsAdmin() != true) {
                    return ResponseDto.noPermission();
                }
            }
            qnaRepository.delete(qnaEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }
    
}
