package com.shop.projectlion.domain.delivery.service;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.repository.DeliveryRepository;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.adminitem.dto.DeliveryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public List<DeliveryDto> findDeliveryDtoList(Long id){
        return deliveryRepository.findDeliveryDtoAllById(id);
    }

    public Delivery findDeliveryJoinMember(Long id){
        return deliveryRepository.findDeliveryJoinMemberById(id).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND_DELIVERY.getMessage()));
    }

    public Delivery findDeliveryById(Long deliveryId) {
        return deliveryRepository.findById(deliveryId).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND_DELIVERY.getMessage()));
    }
}
