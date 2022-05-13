package com.shop.projectlion.web.itemdtl.dto;

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
public class ItemDtlDto {

    private Long itemId;

    private String itemName;

    private Integer price;

    private String itemDetail;

    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    private Integer deliveryFee;

    private List<ItemImageDto> itemImageDtos = new ArrayList<>();

    @Builder
    public ItemDtlDto(Long itemId, String itemName, Integer price, String itemDetail, Integer stockNumber, ItemSellStatus itemSellStatus, Integer deliveryFee, List<ItemImageDto> itemImageDtos) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.itemDetail = itemDetail;
        this.stockNumber = stockNumber;
        this.itemSellStatus = itemSellStatus;
        this.deliveryFee = deliveryFee;
        this.itemImageDtos = itemImageDtos;
    }

    public static ItemDtlDto toItemDtlDto(Item item,List<ItemImageDto> list){
        return ItemDtlDto.builder()
                .itemDetail(item.getDetail())
                .itemName(item.getName())
                .price(item.getPrice())
                .itemId(item.getId())
                .itemSellStatus(item.getSellStatus())
                .deliveryFee(item.getDelivery().getFee())
                .stockNumber(item.getStock())
                .itemImageDtos(list)
                .build();
    }
}
