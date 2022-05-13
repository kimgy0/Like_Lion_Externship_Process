package com.shop.projectlion.web.orderhist.dto;

import com.shop.projectlion.domain.orders.constant.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@ToString
public class OrderHistDto {

    private Long orderId;
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;
    private int totalPrice;
    private int totalDeliveryFee;
    private List<OrderItemHistDto> orderItemHistDtos;

    @Builder
    public OrderHistDto(Long orderId, LocalDateTime orderTime, OrderStatus orderStatus, int totalPrice, int totalDeliveryFee, List<OrderItemHistDto> orderItemHistDtos) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.totalDeliveryFee = totalDeliveryFee;
        this.orderItemHistDtos = orderItemHistDtos;
    }

    @Builder
    public OrderHistDto(Long orderId, LocalDateTime orderTime, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
    }
}