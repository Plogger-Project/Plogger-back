package com.project.plogger.service.Implement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.plogger.common.util.CreateNumber;
import com.project.plogger.dto.request.auth.FindIdAuthCheckDto;
import com.project.plogger.dto.request.auth.FindIdRequestDto;
import com.project.plogger.dto.request.auth.IdCheckRequestDto;
import com.project.plogger.dto.request.auth.SignInRequestDto;
import com.project.plogger.dto.request.auth.SignUpRequestDto;
import com.project.plogger.dto.request.auth.TelAuthCheckRequestDto;
import com.project.plogger.dto.request.auth.TelAuthRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.entity.TelAuthEntity;
import com.project.plogger.entity.UserEntity;
import com.project.plogger.dto.response.auth.FindIdResponseDto;
import com.project.plogger.dto.response.auth.SignInResponseDto;
import com.project.plogger.provider.JwtProvider;
import com.project.plogger.provider.SmsProvider;
import com.project.plogger.repository.TelAuthRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final SmsProvider smsProvider;
    private final JwtProvider jwtProvider;

    private final UserRepository userRepository;
    private final TelAuthRepository telAuthRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto dto) {

        String userId = dto.getUserId();

        try {

            boolean isExisted = userRepository.existsById(userId);
            if (isExisted)
                return ResponseDto.duplicatedUserId();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> telAuth(TelAuthRequestDto dto) {

        String telNumber = dto.getTelNumber();

        try {

            boolean isExisted = userRepository.existsByTelNumber(telNumber);
            if (isExisted)
                return ResponseDto.duplicatedTelNumber();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        String authNumber = new CreateNumber().generateAuthNumber();

        boolean isSendSuccess = smsProvider.sendMessage(telNumber, authNumber);
        if (!isSendSuccess)
            return ResponseDto.messageSendFail();

        try {

            TelAuthEntity telAuthEntity = new TelAuthEntity(telNumber, authNumber);
            telAuthRepository.save(telAuthEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> telAuthCheck(TelAuthCheckRequestDto dto) {

        String telNumber = dto.getTelNumber();
        String authNumber = dto.getAuthNumber();

        try {

            boolean isMatched = telAuthRepository.existsByTelNumberAndAuthNumber(telNumber, authNumber);
            if (!isMatched)
                return ResponseDto.telAuthFail();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();

    }

    @Override
    public ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto) {

        String userId = dto.getUserId();
        String telNumber = dto.getTelNumber();
        String authNumber = dto.getAuthNumber();
        String password = dto.getPassword();

        try {

            boolean isExisted = userRepository.existsById(userId);
            if (isExisted) return ResponseDto.duplicatedUserId();

            boolean isExistedTelNumber = userRepository.existsByTelNumber(telNumber);
            if (isExistedTelNumber) return ResponseDto.duplicatedTelNumber();

            boolean isMatched = telAuthRepository.existsByTelNumberAndAuthNumber(telNumber, authNumber);
            if (!isMatched) return ResponseDto.telAuthFail();

            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {

        String userId = dto.getUserId();
        String password = dto.getPassword();

        String accessToken = null;
        try {

            UserEntity userEntity = userRepository.findByUserId(userId);
            if (userEntity == null)
                return ResponseDto.signInFail();

            String encodedPassword = userEntity.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if (!isMatched)
                return ResponseDto.signInFail();

            accessToken = jwtProvider.create(userId);
            if (accessToken == null)
                return ResponseDto.tokenCreateFail();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(accessToken);
    }

    @Override
    public ResponseEntity<ResponseDto> sendAuthNumber(FindIdAuthCheckDto dto) {

        String telNumber = dto.getTelNumber();

        try {

            boolean isExisted = userRepository.existsByTelNumber(telNumber);
            if (!isExisted)
                return ResponseDto.noExistTelNumber();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        String authNumber = new CreateNumber().generateAuthNumber();

        boolean isSendSuccess = smsProvider.sendMessage(telNumber, authNumber);
        if (!isSendSuccess)
            return ResponseDto.messageSendFail();

        try {

            TelAuthEntity telAuthEntity = new TelAuthEntity(telNumber, authNumber);
            telAuthRepository.save(telAuthEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<? super FindIdResponseDto > findUserIdByTelNumber(FindIdRequestDto dto) {

        try {

            UserEntity userEntity = userRepository.findByTelNumber(dto.getTelNumber());
            if (userEntity == null)
                return ResponseDto.noExistTelNumber();

            return FindIdResponseDto.success(userEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

    }
}
