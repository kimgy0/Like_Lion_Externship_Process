package com.shop.projectlion.domain.image.repository;

import com.shop.projectlion.domain.image.entity.ItemImage;
import com.shop.projectlion.web.adminitem.dto.ItemImageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    @Query(value = "select new com.shop.projectlion.web.adminitem.dto.ItemImageDto(im.id, im.originalName) from ItemImage im join im.item i on i.id=:itemId")
    List<ItemImageDto> findItemImageDtoByItemId(@Param("itemId") Long id);

    @Query(value = "select im from ItemImage im join fetch im.item i where i.id=:itemId")
    List<ItemImage> findItemImageEntityByItemId(@Param("itemId") Long id);

    @Query(value = "select im from ItemImage im join fetch im.item i join fetch i.delivery where i.id = :itemId")
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<ItemImage> findItemAndDelivery(@Param("itemId") Long itemId);
}
