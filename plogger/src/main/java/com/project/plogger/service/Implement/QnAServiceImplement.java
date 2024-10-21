package com.project.plogger.service.Implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.qna.PostQnARequestDto;
import com.project.plogger.dto.response.ResponseDto;
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
    
}
