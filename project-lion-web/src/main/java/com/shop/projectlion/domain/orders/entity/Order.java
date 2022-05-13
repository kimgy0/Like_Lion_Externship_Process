package com.shop.projectlion.domain.orders.entity;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.orderItem.entity.OrderItem;
import com.shop.projectlion.domain.orders.constant.OrderStatus;
import com.shop.projectlion.domain.sub.CommonSubEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends CommonSubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;

    @Column(name = "order_status",nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    @Builder
    public Order(LocalDateTime orderTime, OrderStatus orderStatus) {
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
    }

    public static Order createOrder(Member member, OrderItem orderItem, LocalDateTime localDateTime){

        Order order = Order.builder()
                .orderStatus(OrderStatus.ORDER)
                .orderTime(localDateTime)
                .build();

        order.addMember(member);
        orderItem.addOrder(order);
        return order;
    }

    public void cancel(){
        this.orderStatus = OrderStatus.CANCEL;
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }


}
