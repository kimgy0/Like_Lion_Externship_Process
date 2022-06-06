package com.shop.projectlion.domain.image.repository;

import com.shop.projectlion.domain.image.entity.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    @Query(value = "select im from ItemImage im join fetch im.item i join fetch i.delivery join fetch i.member where i.id = :itemId")
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<ItemImage> findItemAndDelivery(@Param("itemId") Long itemId);
}
