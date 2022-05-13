package com.shop.projectlion.web.orderhist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class OrderItemHistDto {
    private Long orderId;
    private String itemName;
    private int count;
    private int orderPrice;
    private String imageName;
    private int deliveryFee;
}