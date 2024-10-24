package com.project.plogger.service.Implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.repository.RecruitRepository;
import com.project.plogger.repository.TagRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.TagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagServiceImplement implements TagService {
    
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final RecruitRepository recruitRepository;

    @Override
    public ResponseEntity<ResponseDto> postTag(String userId, Integer recruitId) {

        try {

            

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }

    @Override
    public ResponseEntity<ResponseDto> deleteTag(String userId, Integer recruitId) {
        
        try {



        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

}
