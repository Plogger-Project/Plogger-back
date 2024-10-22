package com.project.plogger.entity;

import com.project.plogger.dto.request.gifticon.PatchGifticonRequestDto;
import com.project.plogger.dto.request.gifticon.PostGifticonRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "gifticon")
@Table(name = "gifticon")
public class GifticonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gifticonId;
    private String name;
    private String image;
    private Integer mileageCost;

    public GifticonEntity(PostGifticonRequestDto dto) {
        this.name = dto.getName();
        this.image = dto.getImage();
        this.mileageCost = dto.getMileageCost();
    }

    public void patch(PatchGifticonRequestDto dto) {
        this.name = dto.getName();
        this.image = dto.getImage();
        this.mileageCost = dto.getMileageCost();
    }
}
