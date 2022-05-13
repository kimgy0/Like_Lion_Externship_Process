package com.shop.projectlion.web.adminitem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor
public class DeliveryDto {

    private Long deliveryId;

    private String deliveryName;

    private int deliveryFee;

}