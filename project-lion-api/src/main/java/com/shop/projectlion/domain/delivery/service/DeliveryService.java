package com.shop.projectlion.domain.delivery.service;

import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.repository.DeliveryRepository;
import com.shop.projectlion.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public Delivery findDeliveryById(Long deliveryId) {
        return deliveryRepository.findById(deliveryId).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND_DELIVERY.getMessage()));
    }
}
