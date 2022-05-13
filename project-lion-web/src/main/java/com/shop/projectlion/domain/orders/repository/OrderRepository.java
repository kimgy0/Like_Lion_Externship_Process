package com.shop.projectlion.domain.orders.repository;

import com.shop.projectlion.domain.orders.entity.Order;
import com.shop.projectlion.web.orderhist.dto.OrderHistDto;
import com.shop.projectlion.web.orderhist.dto.OrderItemHistDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query(value = "select new com.shop.projectlion.web.orderhist.dto.OrderHistDto(o.id,o.orderTime,o.orderStatus) from Order o join o.member m on m.id = :memberId",
            countQuery = "select count(o) from Order o" )
    Page<OrderHistDto> findOrderByMemberId(@Param("memberId") Long memberId, Pageable pageable);

    @Query(value = "select distinct new com.shop.projectlion.web.orderhist.dto.OrderItemHistDto(o.id,i.name,oi.count,oi.orderPrice,im.name,d.fee) " +
            "from Order o " +
            "join o.member m " +
            "join o.orderItems oi " +
            "join oi.item i " +
            "join i.delivery d " +
            "join i.itemImages im "+
            "on m.id = :memberId and im.isRep = true")
    List<OrderItemHistDto> findOrderAboutOtherByMemberId(@Param("memberId") Long memberId);

}
