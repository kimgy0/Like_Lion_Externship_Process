package com.shop.projectlion.domain.delivery.entity;

import com.shop.projectlion.domain.item.entitiy.Item;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.sub.CommonSubEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "delivery")
@Getter
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
}
