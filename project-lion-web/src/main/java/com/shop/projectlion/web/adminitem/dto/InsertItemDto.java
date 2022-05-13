package com.shop.projectlion.web.adminitem.dto;

import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter @Setter
public class InsertItemDto {

    @NotBlank(message = "상품명 필수 입력 값 입니다.")
    private String itemName;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    private Integer price;

    @NotBlank(message = "상품 상세는 필수 입력 값입니다.")
    private String itemDetail;

    @NotNull(message = "재고는 필수 입력 값 입니다.")
    private Integer stockNumber;

    private ItemSellStatus itemSellStatus;

    @NotNull(message = "배송 정보는 필수 입니다.")
    private Long deliveryId;

    private List<MultipartFile> itemImageFiles;

}