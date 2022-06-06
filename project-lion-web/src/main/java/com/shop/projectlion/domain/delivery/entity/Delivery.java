package com.shop.projectlion.domain.delivery.entity;

import com.shop.projectlion.domain.item.entitiy.Item;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.sub.CommonSubEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "delivery")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends CommonSubEntity {

    @Id
    @Column(name = "delivery_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "delivery_fee")
    private int fee;

    @Column(name = "delivery_name")
    private String name;

    @OneToMany(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Delivery(Long id, int fee, String name) {
        this.id = id;
        this.fee = fee;
        this.name = name;
    }
}
