package com.shop.projectlion.web.adminitem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.dto.ItemDto;
import com.shop.projectlion.domain.item.entitiy.Item;
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
public class UpdateItemDto extends ItemDto {

    private Long memberId;

    private List<MultipartFile> itemImageFiles;

    @Builder.Default
    private List<ItemImageDto> itemImageDtos = new ArrayList<>();

    private List<String> originalImageNames;

    public UpdateItemDto(Long itemId, Long memberId, String itemName, Integer price, String itemDetail, Integer stockNumber, ItemSellStatus itemSellStatus, Long deliveryId) {
        super.itemId = itemId;
        this.memberId = memberId;
        super.itemName = itemName;
        super.price = price;
        super.itemDetail = itemDetail;
        super.stockNumber = stockNumber;
        super.itemSellStatus = itemSellStatus;
        super.deliveryId = deliveryId;
    }
}