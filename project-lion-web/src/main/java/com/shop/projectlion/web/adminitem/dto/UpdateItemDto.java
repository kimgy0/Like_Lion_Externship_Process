package com.shop.projectlion.web.adminitem.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemDto {

    private Long itemId;

    // 등록자 이외 열람 금지용.
    private Long memberId;

    @NotBlank(message = "상품명 필수 입력 값 입니다.")
    private String itemName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값 입니다.")
    private Integer stockNumber;

    @NotNull(message = "배송 정보는 필수 입니다.")
    private ItemSellStatus itemSellStatus;

    @NotNull(message = "배송 정보는 필수 입니다.")
    private Long deliveryId;

    private List<MultipartFile> itemImageFiles;

    private List<ItemImageDto> itemImageDtos = new ArrayList<>();

    private List<String> originalImageNames;

    public UpdateItemDto(Long itemId, Long memberId, String itemName, Integer price, String itemDetail, Integer stockNumber, ItemSellStatus itemSellStatus, Long deliveryId) {
        this.itemId = itemId;
        this.memberId = memberId;
        this.itemName = itemName;
        this.price = price;
        this.itemDetail = itemDetail;
        this.stockNumber = stockNumber;
        this.itemSellStatus = itemSellStatus;
        this.deliveryId = deliveryId;
    }
}