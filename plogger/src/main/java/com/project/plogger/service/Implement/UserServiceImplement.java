package com.project.plogger.service.Implement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.plogger.common.util.CreateNumber;
import com.project.plogger.dto.request.auth.TelAuthCheckRequestDto;
import com.project.plogger.dto.request.user.ChangeMileageRequestDto;
import com.project.plogger.dto.request.user.PatchTelAuthRequestDto;
import com.project.plogger.dto.request.user.PatchUserRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.admin.GetSignInResponseDto;
import com.project.plogger.entity.GifticonEntity;
import com.project.plogger.entity.TelAuthEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.provider.SmsProvider;
import com.project.plogger.repository.GifticonRepository;
import com.project.plogger.repository.TelAuthRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final SmsProvider smsProvider;
    private final UserRepository userRepository;
    private final TelAuthRepository telAuthRepository;
    private final GifticonRepository gifticonRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<ResponseDto> patchTelAuth(PatchTelAuthRequestDto dto, String userId) {

        String telNumber = dto.getTelNumber();
        String authNumber;

        try {

            UserEntity user = userRepository.findByUserId(userId);
            if (user == null)
                return ResponseDto.noExistUserId();

            boolean isExisted = userRepository.existsByTelNumber(telNumber);
            if (isExisted)
                return ResponseDto.duplicatedTelNumber();

            authNumber = new CreateNumber().generateAuthNumber();
            boolean isSendSuccess = smsProvider.sendMessage(telNumber, authNumber);
            if (!isSendSuccess)
                return ResponseDto.messageSendFail();

            TelAuthEntity telAuthEntity = new TelAuthEntity(telNumber, authNumber);
            telAuthEntity.patch(dto); 
            telAuthRepository.save(telAuthEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> patchTelAuthCheck(TelAuthCheckRequestDto dto, String userId) {

        String telNumber = dto.getTelNumber();
        String authNumber = dto.getAuthNumber();

        try {

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return ResponseDto.noExistUserId();

            boolean isMatched = telAuthRepository.existsByTelNumberAndAuthNumber(telNumber, authNumber);
            if (!isMatched) return ResponseDto.telAuthFail();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> patchUser(PatchUserRequestDto dto, String userId) {
        
        try {

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null) return ResponseDto.noExistUserId();

            String patchUserId = userEntity.getUserId();
            boolean isUser = patchUserId.equals(userId);
            if (!isUser) return ResponseDto.noPermission();

            if (dto.getTelNumber() != null && !dto.getTelNumber().isEmpty()) {
                boolean isExistedTelNumber = userRepository.existsByTelNumber(dto.getTelNumber());
                if (isExistedTelNumber) return ResponseDto.duplicatedTelNumber();

                if (!telAuthRepository.existsByTelNumberAndAuthNumber(dto.getTelNumber(), dto.getAuthNumber())) {
                    return ResponseDto.telAuthFail();
                }
            }

            if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(dto.getPassword());
                userEntity.setPassword(encodedPassword);
            }

            if (dto.getProfileImage() != null) {
                userEntity.setProfileImage(dto.getProfileImage());
            }

            if (dto.getName() != null) {
                userEntity.setName(dto.getName());
            }

            if (dto.getTelNumber() != null) {
                userEntity.setTelNumber(dto.getTelNumber());
            }
            
            if (dto.getAddress() != null) {
                userEntity.setAddress(dto.getAddress());
            }

            userRepository.save(userEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super GetSignInResponseDto> getSignIn(String userId) {

        UserEntity userEntity = null;

        try {

            userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetSignInResponseDto.success(userEntity);
        
    }

    @Override
    public ResponseEntity<ResponseDto> changeMileage(String userId, Integer gifticonId, ChangeMileageRequestDto dto) {
        try {

            GifticonEntity gifticonEntity = gifticonRepository.findByGifticonId(gifticonId);
            if(gifticonEntity == null) return ResponseDto.noExistGifticon();

            UserEntity userEntity = userRepository.findByUserId(userId);
            if(userEntity == null) return ResponseDto.noExistUserId();

            userEntity.patch(dto);
            userRepository.save(userEntity);
            
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }
}