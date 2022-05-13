package com.shop.projectlion.domain.delivery.repository;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.web.adminitem.dto.DeliveryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery,Long> {

    @Query("select new com.shop.projectlion.web.adminitem.dto.DeliveryDto(d.id,d.name,d.fee) from Delivery d join d.member m on m.id = :memberId")
    List<DeliveryDto> findDeliveryDtoAllById(@Param("memberId") Long id);

    @Query("select d from Delivery d join fetch d.member m where d.id=:deliveryId")
    Optional<Delivery> findDeliveryJoinMemberById(@Param("deliveryId") Long id);
}
