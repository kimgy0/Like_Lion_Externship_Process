package com.shop.projectlion.api.item.service;

import com.shop.projectlion.api.item.dto.ItemApiDto;
import com.shop.projectlion.api.item.dto.ItemImageApiDto;
import com.shop.projectlion.api.item.dto.ItemUpdateDto;
import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.image.entity.ItemImage;
import com.shop.projectlion.domain.image.service.ItemImageService;
import com.shop.projectlion.domain.item.entitiy.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.global.constant.BaseConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemApiService {

    private final ItemImageService itemImageService;
    private final ItemService itemService;
    private final DeliveryService deliveryService;

    @Transactional(readOnly = true)
    public ItemApiDto inquiryItem(Long itemId){

        List<ItemImage> imageAndItemAndDelivery = itemImageService.findImageAndItemAndDelivery(itemId);
        Item item = imageAndItemAndDelivery.get(BaseConst.FIRST_ENTITY).getItem();

        List<ItemImageApiDto> itemImageDtos = imageAndItemAndDelivery.stream()
                .map(itemImage -> new ItemImageApiDto(itemImage.getId(),itemImage.getName() != null ? "/images/"+itemImage.getName() : null)).collect(toList());

        ItemApiDto itemApiDto = ItemApiDto.toItemApiDto(item, itemImageDtos);
        return itemApiDto;
    }


    @Transactional
    public ItemUpdateDto updateItem(ItemUpdateDto itemUpdateDto){

        Item item = itemService.findItemAndDeliveryEntity(itemUpdateDto.getItemId());
        Delivery delivery = item.getDelivery();

        if(item.getDelivery().getId() != itemUpdateDto.getDeliveryId()){
            delivery = deliveryService.findDeliveryById(itemUpdateDto.getDeliveryId());
        }
        item.updateItem(itemUpdateDto,delivery);
        return ItemUpdateDto.toDto(item);
    }

}
