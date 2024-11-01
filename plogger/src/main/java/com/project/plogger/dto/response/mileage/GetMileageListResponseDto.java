package com.project.plogger.dto.response.mileage;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.Mileage;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.MileageEntity;

import lombok.Getter;

@Getter
public class GetMileageListResponseDto extends ResponseDto{

    private List<Mileage> mileages;

    public GetMileageListResponseDto(List<MileageEntity> mileageEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.mileages = Mileage.getList(mileageEntities);
    }

    public static ResponseEntity<GetMileageListResponseDto> success(List<MileageEntity> mileageEntities) {
        GetMileageListResponseDto responseBody = new GetMileageListResponseDto(mileageEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
