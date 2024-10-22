package com.project.plogger.service.Implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.recruit.PostRecruitRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitResponseDto;
import com.project.plogger.repository.RecruitRepository;
import com.project.plogger.repository.resultSet.GetRecruitResultSet;
import com.project.plogger.service.RecruitService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitServiceImplement implements RecruitService {
    
    private final RecruitRepository recruitRepository;
    
    @Override
    public ResponseEntity<? super GetRecruitResponseDto> getRecruit(Integer recruitPostId) {
        GetRecruitResultSet resultSet = null;
        try {
            resultSet = recruitRepository.getRecruit(recruitPostId);
            if (resultSet == null) {
                return ResponseDto.noExistRecruit();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetRecruitResponseDto.success(resultSet);

    }

    @Override
    public ResponseEntity<ResponseDto> postRecruit(PostRecruitRequestDto dto) {
        try {
            

            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return ResponseDto.success();
    }
    
}
