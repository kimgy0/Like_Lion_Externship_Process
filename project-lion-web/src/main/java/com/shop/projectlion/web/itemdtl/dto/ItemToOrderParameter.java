package com.shop.projectlion.web.itemdtl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ItemToOrderParameter {
    private String itemId;
    private String count;
}
