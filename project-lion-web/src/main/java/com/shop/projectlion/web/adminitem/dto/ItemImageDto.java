package com.shop.projectlion.web.adminitem.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ItemImageDto {
    private Long itemImageId;
    private String originalImageName;

    public ItemImageDto(Long itemImageId, String originalImageName) {
        this.itemImageId = itemImageId;
        this.originalImageName = originalImageName;
    }
}