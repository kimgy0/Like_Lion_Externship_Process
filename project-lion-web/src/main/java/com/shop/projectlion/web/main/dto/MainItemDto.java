package com.shop.projectlion.web.main.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@Getter @Setter
@ToString
public class MainItemDto {

    private Long itemId;

    private String itemName;

    private String itemDetail;

    private String imageName;

    private Integer price;

}
