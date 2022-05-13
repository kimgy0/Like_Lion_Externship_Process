package com.shop.projectlion.domain.orderItem.service;

import com.shop.projectlion.domain.orderItem.entity.OrderItem;
import com.shop.projectlion.domain.orderItem.repositroy.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public List<OrderItem> findOrderItemAndOrderEntity(Long orderId){
        return orderItemRepository.findOrderItemAndOrderEntityById(orderId);
    }
}
