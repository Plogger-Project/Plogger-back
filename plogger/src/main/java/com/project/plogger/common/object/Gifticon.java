package com.project.plogger.common.object;

import java.util.ArrayList;
import java.util.List;

import com.project.plogger.entity.GifticonEntity;

import lombok.Getter;

@Getter
public class Gifticon {

    private Integer gifticonId;
    private String name;
    private String image;
    private Integer mileageCost;

    private Gifticon(GifticonEntity gifticonEntity) {
        this.gifticonId = gifticonEntity.getGifticonId();
        this.name = gifticonEntity.getName();
        this.image = gifticonEntity.getImage();
        this.mileageCost = gifticonEntity.getMileageCost();
    }

    public static List<Gifticon> getList(List<GifticonEntity> gifticonEntities) {

        List<Gifticon> gifticons = new ArrayList<>();
        for(GifticonEntity gifticonEntity: gifticonEntities) {
            Gifticon gifticon = new Gifticon(gifticonEntity);
            gifticons.add(gifticon);
        }

        return gifticons;

    }
}
