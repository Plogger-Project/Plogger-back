package com.project.plogger.service.Implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.project.plogger.dto.request.recruit.RecruitReportRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.recruit.GetRecruitReportListResponseDto;
import com.project.plogger.entity.RecruitEntity;
import com.project.plogger.entity.RecruitReportEntity;
import com.project.plogger.repository.RecruitReportRepository;
import com.project.plogger.repository.RecruitRepository;
import com.project.plogger.repository.UserRepository;
import com.project.plogger.service.RecruitReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitReportServiceImplement implements RecruitReportService {

    private final RecruitRepository recruitRepository;
    private final RecruitReportRepository recruitReportRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ResponseDto> createReport(Integer recruitId, String userId,
            RecruitReportRequestDto dto) {
        try {
            boolean isAlreadyReported = recruitReportRepository.existsByRecruitIdAndUserId(recruitId, userId);

            if (isAlreadyReported) {
                return ResponseDto.duplicatedReport();
            }

            RecruitEntity recruitEntity = recruitRepository.findByRecruitPostId(recruitId);

            if (recruitEntity == null) {
                return ResponseDto.noExistRecruitReport();
            }
            RecruitReportEntity recruitReportEntity = new RecruitReportEntity(dto, recruitId, userId);

            recruitEntity.setRecruitReport(recruitEntity.getRecruitReport() + 1);
            recruitRepository.save(recruitEntity);            
            recruitReportRepository.save(recruitReportEntity);

            return ResponseDto.success();

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super GetRecruitReportListResponseDto> getAllRecruitReportPost() {
        
        List<RecruitReportEntity> recruitReportEntities = new ArrayList<>();

        try {
            recruitReportEntities = recruitReportRepository.findAllByOrderByReportIdDesc();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetRecruitReportListResponseDto.success(recruitReportEntities);
    }

}
