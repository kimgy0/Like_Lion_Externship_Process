package com.shop.projectlion.domain.image.service;

import com.shop.projectlion.domain.image.entity.ItemImage;
import com.shop.projectlion.domain.image.repository.ItemImageRepository;
import com.shop.projectlion.web.adminitem.dto.ItemImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImageService {
    private final ItemImageRepository itemImageRepository;

    public List<ItemImageDto> findItemImageForItem(Long itemId){
        return itemImageRepository.findItemImageDtoByItemId(itemId);
    }

    public List<ItemImage> findItemImageEntityForItem(Long itemId){
        return itemImageRepository.findItemImageEntityByItemId(itemId);
    }

    @Transactional(readOnly = true)
    public List<ItemImage> findImageAndItemAndDelivery(Long itemId){
        return itemImageRepository.findItemAndDelivery(itemId);
    }
}
