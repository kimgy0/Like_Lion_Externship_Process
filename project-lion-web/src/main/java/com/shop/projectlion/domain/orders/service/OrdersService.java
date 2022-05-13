package com.shop.projectlion.domain.orders.service;

import com.shop.projectlion.domain.orders.entity.Order;
import com.shop.projectlion.domain.orders.repository.OrderRepository;
import com.shop.projectlion.web.orderhist.dto.OrderHistDto;
import com.shop.projectlion.web.orderhist.dto.OrderItemHistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrdersService {

    private final OrderRepository orderRepository;

    public void save(Order order){
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<OrderItemHistDto> findOrderOfOtherDtoByMemberId(Long memberId){
        return orderRepository.findOrderAboutOtherByMemberId(memberId);
    }


    @Transactional(readOnly = true)
    public Page<OrderHistDto> findOrderDtoByMemberId(Long memberId, Pageable pageable) {
        return orderRepository.findOrderByMemberId(memberId, pageable);
    }

}
