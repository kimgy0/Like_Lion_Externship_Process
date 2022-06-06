package com.shop.projectlion.domain.item.repository;

import com.shop.projectlion.domain.item.entitiy.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>{

    @Query("select i from Item i join fetch i.delivery d join fetch i.member m where i.id = :itemId")
    Optional<Item> findItemAndDeliveryEntityById(@Param("itemId") Long itemId);
}
