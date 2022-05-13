package com.shop.projectlion.web.orderhist.service;

import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.domain.orderItem.entity.OrderItem;
import com.shop.projectlion.domain.orderItem.service.OrderItemService;
import com.shop.projectlion.domain.orders.entity.Order;
import com.shop.projectlion.domain.orders.service.OrdersService;
import com.shop.projectlion.global.constant.BaseConst;
import com.shop.projectlion.web.orderhist.dto.OrderHistDto;
import com.shop.projectlion.web.orderhist.dto.OrderItemHistDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHistService {

    private final MemberService memberService;
    private final OrdersService orderService;
    private final OrderItemService orderItemService;
    
    @Transactional(readOnly = true)
    public Page<OrderHistDto> orderHistPagePresent(Pageable pageable){
        Long memberId = memberService.getMyMemberId();

        Page<OrderHistDto> pageOrderHistDto = orderService.findOrderDtoByMemberId(memberId, pageable);
        List<OrderItemHistDto> orderOfOtherDto = orderService.findOrderOfOtherDtoByMemberId(memberId);

        return pageOrderHistDto.map(oldOrderHistDto -> {return addToOrderHistDto(oldOrderHistDto, orderOfOtherDto);});

    }


    private OrderHistDto addToOrderHistDto(OrderHistDto oldOrderHistDto,
                                           List<OrderItemHistDto> orderOtherInfo) {

        AtomicInteger totalPrice = new AtomicInteger();
        AtomicInteger totalDeliveryFee = new AtomicInteger();
        List<OrderItemHistDto> injectOrderItemHist = new ArrayList<>();

        /* 장바구니 시스템을 대비한 총 가격, 배송가격, 이미지를 삽입하는 로직 */
        orderOtherInfo.forEach(orderItemHistDto -> {
            if(isSameOrderItem(oldOrderHistDto, orderItemHistDto)){

                injectOrderItemHist.add(orderItemHistDto);
                totalPrice.addAndGet(orderItemHistDto.getOrderPrice());
                totalDeliveryFee.addAndGet(orderItemHistDto.getDeliveryFee());

            }
        });

        OrderHistDto orderHistDto = OrderHistDto.builder()
                .orderId(oldOrderHistDto.getOrderId())
                .orderStatus(oldOrderHistDto.getOrderStatus())
                .orderTime(oldOrderHistDto.getOrderTime())
                .totalPrice(totalPrice.intValue())
                .totalDeliveryFee(totalDeliveryFee.intValue())
                .orderItemHistDtos(injectOrderItemHist)
                .build();
        return orderHistDto;
    }

    private boolean isSameOrderItem(OrderHistDto oldOrderHistDto, OrderItemHistDto orderItemHistDto) {
        return orderItemHistDto.getOrderId() == oldOrderHistDto.getOrderId();
    }

    public void orderCancelFunction(Long orderId) {
        List<OrderItem> orderItems = orderItemService.findOrderItemAndOrderEntity(orderId);
        Order order = orderItems.get(BaseConst.FIRST_ENTITY).getOrder();
        order.cancel();

    }
}
