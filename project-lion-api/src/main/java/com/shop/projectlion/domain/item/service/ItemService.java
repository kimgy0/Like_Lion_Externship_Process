package com.shop.projectlion.domain.item.service;

import com.shop.projectlion.domain.item.entitiy.Item;
import com.shop.projectlion.domain.item.repository.ItemRepository;
import com.shop.projectlion.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item findItemAndDeliveryEntity(Long id){
        return itemRepository.findItemAndDeliveryEntityById(id).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND_ITEM.getMessage()));
    }
}
