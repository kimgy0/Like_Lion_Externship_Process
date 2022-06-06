package com.shop.projectlion.web.itemdtl.service;

import com.shop.projectlion.domain.image.entity.ItemImage;
import com.shop.projectlion.domain.item.entitiy.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.domain.orderItem.entity.OrderItem;
import com.shop.projectlion.domain.orders.entity.Order;
import com.shop.projectlion.domain.orders.service.OrdersService;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.web.itemdtl.dto.ItemDtlDto;
import com.shop.projectlion.web.itemdtl.dto.ItemImageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ItemDtlService {

    private final com.shop.projectlion.domain.image.service.ItemImageService itemImageService;
    private final ItemService itemService;
    private final MemberService memberService;
    private final OrdersService orderService;

    @Transactional(readOnly = true)
    public ItemDtlDto itemDetailsInquire(Long itemId){
        List<ItemImage> imageAndItemAndDelivery = itemImageService.findImageAndItemAndDelivery(itemId);

        List<ItemImageDto> itemImageDtos = imageAndItemAndDelivery.stream().map(itemImage -> new ItemImageDto(itemImage.getName())).collect(toList());
        return ItemDtlDto.toItemDtlDto(imageAndItemAndDelivery.get(0).getItem(), itemImageDtos);
    }

    public void order(Long itemId, int count){
        Member member = memberService.findMemberEntityById(memberService.getMyMemberId());
        Item item = itemService.findItemAndDeliveryEntity(itemId);

        try {
            OrderItem orderItem = OrderItem.createOrderItem(item, count);
            Order order = Order.createOrder(member, orderItem, LocalDateTime.now());
            orderService.save(order);
        }catch (BusinessException e){
            throw e;
        }

    }
}
