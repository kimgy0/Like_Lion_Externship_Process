package com.shop.projectlion.api.item.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entitiy.Item;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
public class ItemUpdateDto{

    @ApiModelProperty(example = "1")
    protected Long itemId;

    @ApiModelProperty(example = "테스트 상품명")
    @NotBlank(message = "상품명 필수 입력 값 입니다.")
    protected String itemName;

    @ApiModelProperty(example = "30000")
    @NotNull(message = "가격은 필수 입력 값입니다.")
    protected Integer price;

    @ApiModelProperty(example = "테스트 상품 상세", required = true)
    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    protected String itemDetail;

    @ApiModelProperty(example = "999")
    @NotNull(message = "재고는 필수 입력 값 입니다.")
    protected Integer stockNumber;

    @ApiModelProperty(example = "SELL")
    @NotNull(message = "배송 정보는 필수 입니다.")
    protected ItemSellStatus itemSellStatus;

    @ApiModelProperty(example = "3")
    @NotNull(message = "배송 정보는 필수 입니다.")
    protected Long deliveryId;

    @Builder
    public ItemUpdateDto(Long itemId, String itemName, Integer price, String itemDetail, Integer stockNumber, ItemSellStatus itemSellStatus, Long deliveryId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.itemDetail = itemDetail;
        this.stockNumber = stockNumber;
        this.itemSellStatus = itemSellStatus;
        this.deliveryId = deliveryId;
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
