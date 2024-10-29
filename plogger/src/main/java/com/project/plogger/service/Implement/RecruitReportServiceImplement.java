package com.project.plogger.service.Implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.project.plogger.dto.request.recruit.RecruitReportRequestDto;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.entity.RecruitEntity;
import com.project.plogger.entity.RecruitReportEntity;
import com.project.plogger.repository.RecruitReportRepository;
import com.project.plogger.repository.RecruitRepository;
import com.project.plogger.service.RecruitReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecruitReportServiceImplement implements RecruitReportService {

    private final RecruitRepository recruitRepository;
    private final RecruitReportRepository recruitReportRepository;

    @Override
    public ResponseEntity<ResponseDto> createReport(Integer recruitId, String userId,
            RecruitReportRequestDto dto) {
        try {
            RecruitEntity recruitEntity = recruitRepository.findByRecruitPostId(recruitId);
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

}
