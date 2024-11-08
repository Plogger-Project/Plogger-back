package com.project.plogger.service.Implement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.plogger.dto.request.active.ActiveReportRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.active.GetActiveReportListResponseDto;
import com.project.plogger.entity.ActivePostEntity;
import com.project.plogger.entity.ActiveReportEntity;
import com.project.plogger.repository.ActivePostRepository;
import com.project.plogger.repository.ActiveReportRepository;

import com.project.plogger.service.ActiveReportService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ActiveReportServiceImplement implements ActiveReportService {

    private final ActivePostRepository activeRepository;
    private final ActiveReportRepository activeReportRepository;

    @Override
    public ResponseEntity<ResponseDto> createReport(Integer activeId, String userId, ActiveReportRequestDto dto) {
        try {
            boolean isAlreadyReported = activeReportRepository.existsByActiveIdAndUserId(activeId, userId);

            if (isAlreadyReported) {
                return ResponseDto.duplicatedReport();
            }

            ActivePostEntity activePostEntity = activeRepository.findByActivePostId(activeId);
            ActiveReportEntity activeReportEntity = new ActiveReportEntity(dto, activeId, userId);

            if (activePostEntity == null) {
                return ResponseDto.duplicatedReport();
            }

            activePostEntity.setActiveReport(activePostEntity.getActiveReport() + 1);
            activeRepository.save(activePostEntity);

            activeReportRepository.save(activeReportEntity);

            return ResponseDto.success();
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super GetActiveReportListResponseDto> getAllActiveReportPost() {
        
        List<ActiveReportEntity> activeReportEntities = new ArrayList<>();

        try {
            activeReportEntities = activeReportRepository.findAllByOrderByReportIdDesc();
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetActiveReportListResponseDto.success(activeReportEntities);
    }

}
