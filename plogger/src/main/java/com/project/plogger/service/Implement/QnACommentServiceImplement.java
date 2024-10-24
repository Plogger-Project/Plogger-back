package com.project.plogger.service.Implement;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.qna.PatchQnACommentRequestDto;
import com.project.plogger.dto.request.qna.PostQnACommentRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.qna.GetQnACommentListResponseDto;
import com.project.plogger.entity.QnACommentEntity;
import com.project.plogger.entity.QnAEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.repository.QnACommentRepository;
import com.project.plogger.repository.QnARepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.QnACommentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnACommentServiceImplement implements QnACommentService{

    private final UserRepository userRepository;
    private final QnARepository qnaRepository;
    private final QnACommentRepository qnaCommentRepository;

    @Override
    public ResponseEntity<ResponseDto> postQnAComment(PostQnACommentRequestDto dto, String userId, Integer qnaId) {

        try {
            
            QnACommentEntity qnaCommentEntity = new QnACommentEntity(dto);

            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();
            qnaCommentEntity.setQnaCommentWriter(userId);

            QnAEntity qnaEntity = qnaRepository.findByQnaPostId(qnaId);
            if(qnaEntity == null) return ResponseDto.noExistQnA();
            qnaCommentEntity.setQnaPostId(qnaId);
            
            qnaCommentEntity.setQnaCommentCreatedAt();

            qnaCommentRepository.save(qnaCommentEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetQnACommentListResponseDto> getQnACommentList(Integer qnaId) {

        List<QnACommentEntity> qnaCommentEntities = new ArrayList<>();

        try {

            qnaCommentEntities = qnaCommentRepository.findByQnaIdOrderByQnaCommentIdAsc(qnaId);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetQnACommentListResponseDto.success(qnaCommentEntities);

    }

    @Override
    public ResponseEntity<ResponseDto> patchQnAComment(Integer qnaId, Integer qnaCommentId, String userId, PatchQnACommentRequestDto dto) {

        try {

            QnACommentEntity qnaCommentEntity = qnaCommentRepository.findByQnaCommentId(qnaCommentId);
            if(qnaCommentEntity == null) return ResponseDto.noExistQnAComment();

            if(!qnaCommentEntity.getQnaId().equals(qnaId)) return ResponseDto.noExistQnA();
            if(!qnaCommentEntity.getQnaCommentWriter().equals(userId)) return ResponseDto.noPermission();

            qnaCommentEntity.patch(dto);
            qnaCommentEntity.setQnaCommentCreatedAt();

            qnaCommentRepository.save(qnaCommentEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }

    @Override
    public ResponseEntity<ResponseDto> deleteQnAComment(Integer qnaId, Integer qnaCommentId, String userId) {

        try {

            QnACommentEntity qnaCommentEntity = qnaCommentRepository.findByQnaCommentId(qnaCommentId);
            if(qnaCommentEntity == null) return ResponseDto.noExistQnAComment();

            if(!qnaCommentEntity.getQnaId().equals(qnaId)) return ResponseDto.noExistQnA();
            if(!qnaCommentEntity.getQnaCommentWriter().equals(userId)) return ResponseDto.noPermission();     

            qnaCommentRepository.delete(qnaCommentEntity);

            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }
    
}
