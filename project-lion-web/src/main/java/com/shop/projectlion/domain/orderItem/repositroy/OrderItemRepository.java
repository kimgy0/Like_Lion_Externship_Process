package com.shop.projectlion.domain.orderItem.repositroy;

import com.shop.projectlion.domain.orderItem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem , Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select oi from OrderItem oi join fetch oi.order o join fetch oi.item i where o.id = :orderId")
    List<OrderItem> findOrderItemAndOrderEntityById(@Param("orderId") Long orderId);

}
