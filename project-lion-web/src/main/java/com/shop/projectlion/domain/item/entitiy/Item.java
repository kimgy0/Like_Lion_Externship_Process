package com.shop.projectlion.domain.item.entitiy;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.image.entity.ItemImage;
import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.sub.CommonSubEntity;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.adminitem.dto.InsertItemDto;
import com.shop.projectlion.web.adminitem.dto.UpdateItemDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Item extends CommonSubEntity {

    @Id
    @Column(name = "item_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_detail", nullable = false)
    private String detail;

    @Column(name = "item_name", nullable = false)
    private String name;

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

    public void plusStock(int orderStock){
        this.stock+=orderStock;
        if(stock > 0){
            this.sellStatus = ItemSellStatus.SELL;
        }
    }

    public void minusStock(int cancelStock){
        int restStock = this.stock - cancelStock;
        if(restStock < 0){
            throw new BusinessException(ErrorCode.OUT_OF_STOCK_BOUND);
        }else if (restStock == 0){
            this.sellStatus = ItemSellStatus.SOLD_OUT;
        }
        this.stock -= cancelStock;
    }

    public void updateItem(UpdateItemDto updateItemDto, Delivery delivery) {
        this.detail = updateItemDto.getItemDetail();
        this.name = updateItemDto.getItemName();
        this.price = updateItemDto.getPrice();
        this.stock = updateItemDto.getStockNumber();
        this.sellStatus = updateItemDto.getItemSellStatus();
        this.delivery = delivery;
    }

    public void addMember(Member member){
        this.member = member;
        member.getItems().add(this);
    }

    public void addDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.getItems().add(this);
    }

    public static Item toEntity(InsertItemDto insertItemDto){
        return Item.builder()
                .itemImages(new ArrayList<>())
                .stock(insertItemDto.getStockNumber())
                .detail(insertItemDto.getItemDetail())
                .name(insertItemDto.getItemName())
                .price(insertItemDto.getPrice())
                .sellStatus(insertItemDto.getItemSellStatus())
                .build();
    }

    public static Item registerItem(InsertItemDto insertItemDto, Delivery delivery, Member member, List<ItemImage> itemImages){
        Item item = toEntity(insertItemDto);
        item.addDelivery(delivery);
        item.addMember(member);
        for (ItemImage image : itemImages) {
            image.addItem(item);
        }
        return item;
    }
}