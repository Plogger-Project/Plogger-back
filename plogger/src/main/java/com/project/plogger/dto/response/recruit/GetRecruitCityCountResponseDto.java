package com.project.plogger.dto.response.recruit;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.CityCount;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.repository.resultset.CityPostCountResultSet;

import lombok.Getter;

@Getter
public class GetRecruitCityCountResponseDto extends ResponseDto {
    
    private List<CityCount> addressPostCounts;

    public GetRecruitCityCountResponseDto(List<CityPostCountResultSet> resultSets) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.addressPostCounts = CityCount.getList(resultSets);
    }

    public static ResponseEntity<GetRecruitCityCountResponseDto> success(List<CityPostCountResultSet> resultSets) {
        GetRecruitCityCountResponseDto responseBody = new GetRecruitCityCountResponseDto(resultSets);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
