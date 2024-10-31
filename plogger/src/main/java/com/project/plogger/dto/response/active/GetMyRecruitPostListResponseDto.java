package com.project.plogger.dto.response.active;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.MyRecruit;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.RecruitEntity;
import com.project.plogger.repository.RecruitJoinRepository;

import lombok.Getter;

@Getter
public class GetMyRecruitPostListResponseDto extends ResponseDto {

    private List<MyRecruit> myRecruitPosts;

    // RecruitJoinRepository를 추가
    public GetMyRecruitPostListResponseDto(List<RecruitEntity> recruitEntities, RecruitJoinRepository joinRepository) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.myRecruitPosts = MyRecruit.getList(recruitEntities, joinRepository); 
    }

    public static ResponseEntity<GetMyRecruitPostListResponseDto> success(List<RecruitEntity> recruitEntities, RecruitJoinRepository joinRepository) {
        GetMyRecruitPostListResponseDto responseBody = new GetMyRecruitPostListResponseDto(recruitEntities, joinRepository);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
