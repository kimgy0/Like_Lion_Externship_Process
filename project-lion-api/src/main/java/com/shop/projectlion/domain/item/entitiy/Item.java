package com.shop.projectlion.domain.item.entitiy;

import com.shop.projectlion.api.item.dto.ItemUpdateDto;
import com.shop.projectlion.domain.common.BaseEntity;
import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.image.entity.ItemImage;
import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id
    @Column(name = "item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_detail", nullable = false)
    private String detail;

    @Column(name = "item_name", nullable = false)
    private String name;

    @Builder
    public Item(Long id, String detail, String name, int price, int stock, ItemSellStatus sellStatus, Delivery delivery) {
        this.id = id;
        this.detail = detail;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.sellStatus = sellStatus;
        this.delivery = delivery;
    }

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "stock_number", nullable = false)
    private int stock;

    @Column(name = "item_sell_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ItemSellStatus sellStatus;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImage> itemImages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;


    public void updateItem(ItemUpdateDto updateItemDto, Delivery delivery) {
        this.detail = updateItemDto.getItemDetail();
        this.name = updateItemDto.getItemName();
        this.price = updateItemDto.getPrice();
        this.stock = updateItemDto.getStockNumber();
        this.sellStatus = updateItemDto.getItemSellStatus();
        this.delivery = delivery;
    }

    @Builder
    public Item(String detail, String name, int price, int stock, ItemSellStatus sellStatus, List<ItemImage> itemImages) {
        this.detail = detail;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.sellStatus = sellStatus;
        this.itemImages = itemImages;
    }
}