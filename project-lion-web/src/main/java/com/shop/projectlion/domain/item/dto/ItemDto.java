package com.shop.projectlion.domain.item.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class ItemDto {
    @ApiModelProperty(example = "1")
    protected Long itemId;

    @ApiModelProperty(example = "테스트 상품명")
    @NotBlank(message = "상품명 필수 입력 값 입니다.")
    protected String itemName;

    @ApiModelProperty(example = "30000")
    @NotNull(message = "가격은 필수 입력 값입니다.")
    protected Integer price;

    @ApiModelProperty(example = "테스트 상품 상세")
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
}
