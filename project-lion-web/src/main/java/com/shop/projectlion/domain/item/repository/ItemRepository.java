package com.shop.projectlion.domain.item.repository;

import com.shop.projectlion.domain.item.entitiy.Item;
import com.shop.projectlion.domain.item.repository.custom.ItemRepositoryCustom;
import com.shop.projectlion.web.adminitem.dto.UpdateItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> , ItemRepositoryCustom{

    @Query("select new com.shop.projectlion.web.adminitem.dto.UpdateItemDto(i.id, m.id, i.name, i.price, i.detail, i.stock, i.sellStatus, d.id) " +
            "from Item i " +
            "join i.member m " +
            "join i.delivery d " +
            "on i.id = :itemId")
    Optional<UpdateItemDto> findAllById(@Param("itemId") Long id);

    @Query("select i from Item i join fetch Delivery d on i.id=:itemId")
    Optional<Item> findJoinDeliveryById(@Param("itemId") Long id);

    @Query("select i from Item i join fetch i.delivery d where i.id = :itemId")
    Optional<Item> findItemAndDeliveryEntityById(@Param("itemId") Long itemId);
}
