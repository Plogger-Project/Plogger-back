package com.project.plogger.dto.response.gifticon;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.common.object.Gifticon;
import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.GifticonEntity;

import lombok.Getter;

@Getter
public class GetGifticonListResponseDto extends ResponseDto {

    private List<Gifticon> gifticons;

    public GetGifticonListResponseDto(List<GifticonEntity> gifticonEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.gifticons = Gifticon.getList(gifticonEntities);
    }

    public static ResponseEntity<GetGifticonListResponseDto> success(List<GifticonEntity> gifticonEntities) {
        GetGifticonListResponseDto responseBody = new GetGifticonListResponseDto(gifticonEntities);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
    
}
