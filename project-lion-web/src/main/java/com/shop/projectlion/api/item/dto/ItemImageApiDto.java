package com.shop.projectlion.api.item.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class ItemImageApiDto {
    private Long itemImageId;
    private String imageUrl;
}

