package com.shop.projectlion.api.item.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entitiy.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
public class ItemApiDto {
    private Long itemId;
    private String itemName;
    private Integer price;
    private String itemDetail;
    private Integer stockNumber;
    private ItemSellStatus itemSellStatus;
    private Integer deliveryFee;
    private List<ItemImageApiDto> itemImageApiDtos = new ArrayList<>();

    @Builder
    public ItemApiDto(Long itemId, String itemName, Integer price, String itemDetail, Integer stockNumber, ItemSellStatus itemSellStatus, Integer deliveryFee, List<ItemImageApiDto> itemImageDtos) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.itemDetail = itemDetail;
        this.stockNumber = stockNumber;
        this.itemSellStatus = itemSellStatus;
        this.deliveryFee = deliveryFee;
        this.itemImageApiDtos = itemImageDtos;
    }

    public static ItemApiDto toItemApiDto(Item item, List<ItemImageApiDto> list){
        return ItemApiDto.builder()
                .itemImageDtos(list)
                .itemDetail(item.getDetail())
                .itemName(item.getName())
                .price(item.getPrice())
                .itemId(item.getId())
                .itemSellStatus(item.getSellStatus())
                .deliveryFee(item.getDelivery().getFee())
                .stockNumber(item.getStock())
                .build();
    }
}
