package com.project.plogger.dto.response.recruit;

import java.util.List;

import com.project.plogger.common.object.Recruit;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.RecruitEntity;

import lombok.Getter;

@Getter
public class GetRecruitListResponseDto extends ResponseDto{
    
    private List<Recruit> recruits;

    private GetRecruitListResponseDto(List<RecruitEntity> recruitEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.recruits = Recruit.getList(recruitEntities);
    }
}
