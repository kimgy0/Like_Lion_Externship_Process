package com.shop.projectlion.domain.item.service;

import com.shop.projectlion.domain.item.entitiy.Item;
import com.shop.projectlion.domain.item.repository.ItemRepository;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.adminitem.dto.UpdateItemDto;
import com.shop.projectlion.web.main.dto.MainItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item createItem(Item item){
        return itemRepository.save(item);
    }

    public UpdateItemDto findUpdateFormForItem(Long id){
        return itemRepository.findAllById(id).orElseThrow( () -> new IllegalArgumentException(ErrorCode.NOT_FOUND_ITEM.getMessage()));
    }

    // 메인 페이지에서 검색 결과와 일반 메인 페이지 관심사 응집도 향상.
    public Page<MainItemDto> findAllMainItemDtoAndSearch(String searchName , Pageable pageable){
        return itemRepository.findItemInfoAllCustom(searchName, pageable);
    }

    public Item findItemAndDeliveryEntity(Long id){
        return itemRepository.findItemAndDeliveryEntityById(id).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND_ITEM.getMessage()));
    }
}
