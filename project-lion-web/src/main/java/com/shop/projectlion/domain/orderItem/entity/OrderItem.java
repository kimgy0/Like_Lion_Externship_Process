package com.shop.projectlion.domain.orderItem.entity;

import com.shop.projectlion.domain.item.entitiy.Item;
import com.shop.projectlion.domain.orders.entity.Order;
import com.shop.projectlion.domain.sub.CommonSubEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends CommonSubEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "order_price", nullable = false)
    private Integer orderPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void addOrder(Order order){
        this.order = order;
        order.getOrderItems().add(this);
    }

    public void addItem(Item item){
        this.item = item;
        // item 쪽의 orderItems 는 일단 생략 (알 필요가 없을 경우를 위함)
    }

    @Builder
    public OrderItem(int count, int orderPrice, Order order, Item item) {
        this.count = count;
        this.orderPrice = orderPrice;
        this.order = order;
        this.item = item;
    }


    public static OrderItem createOrderItem(Item item, int count){
        item.minusStock(count);
        return OrderItem.builder()
                .count(count)
                .orderPrice(getOrderPrice(item, count))
                .item(item) // addItem
                .build();
    }

    private static int getOrderPrice(Item item, int count) {
        return item.getPrice() * count;
    }


    public void cancel(){
        this.item.plusStock(count);
    }



}
