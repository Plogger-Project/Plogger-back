package com.project.plogger.service.Implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.admin.GetSignInResponseDto;
import com.project.plogger.dto.response.admin.GetUserListResponseDto;
import com.project.plogger.dto.response.admin.GetUserResponseDto;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImplement implements AdminService{
    
    private final UserRepository userRepository;
    
    @Override
    public ResponseEntity<? super GetUserListResponseDto> getUserList() {
        
        List<UserEntity> userEntities = new ArrayList<>();

        try {

            userEntities = userRepository.findAll();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        
        return GetUserListResponseDto.success(userEntities);
    }

    @Override
    public ResponseEntity<? super GetSignInResponseDto> getSignIn(String userId) {
        
        UserEntity userEntity = null;

        try {
            
            userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return ResponseDto.noExistUserId();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetSignInResponseDto.success(userEntity);
    }

    @Override
    public ResponseEntity<? super GetUserResponseDto> getUser(String userId) {
        
        UserEntity userEntity = null;

        try {
            
            userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return ResponseDto.noExistUserId();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetUserResponseDto.success(userEntity);
    }
    
}
