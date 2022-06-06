package com.shop.projectlion.api.item.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.dto.ItemDto;
import com.shop.projectlion.domain.item.entitiy.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemUpdateDto extends ItemDto {

    @Builder
    public ItemUpdateDto(Long itemId,  String itemName, Integer price, String itemDetail,  Integer stockNumber, ItemSellStatus itemSellStatus,  Long deliveryId) {
        super(itemId, itemName, price, itemDetail, stockNumber, itemSellStatus, deliveryId);
    }

    public static ItemUpdateDto toDto(Item item){
        return ItemUpdateDto.builder().itemDetail(item.getDetail())
                .itemId(item.getId())
                .itemName(item.getName())
                .price(item.getPrice())
                .itemSellStatus(item.getSellStatus())
                .deliveryId(item.getDelivery().getId())
                .stockNumber(item.getStock())
                .build();
    }
}
