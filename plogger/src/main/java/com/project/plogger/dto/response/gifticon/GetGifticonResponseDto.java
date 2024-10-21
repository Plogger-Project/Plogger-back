package com.project.plogger.dto.response.gifticon;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.project.plogger.dto.response.ResponseCode;
import com.project.plogger.dto.response.ResponseDto;
import com.project.plogger.dto.response.ResponseMessage;
import com.project.plogger.entity.GifticonEntity;

import lombok.Getter;

@Getter
public class GetGifticonResponseDto extends ResponseDto{

    private Integer gifticonId;
    private String name;
    private String image;
    private Integer mileageCost;

    private GetGifticonResponseDto(GifticonEntity gifticonEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.gifticonId = gifticonEntity.getGifticonId();
        this.name = gifticonEntity.getName();
        this.image = gifticonEntity.getImage();
        this.mileageCost = gifticonEntity.getMileageCost();
    }

    public static ResponseEntity<GetGifticonResponseDto> success(GifticonEntity gifticonEntity) {
        GetGifticonResponseDto responseBody = new GetGifticonResponseDto(gifticonEntity);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
