package com.shop.projectlion.web.main.service;

import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.web.main.dto.MainItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainService {

    private final ItemService itemService;

    public Page<MainItemDto> mainPagePresent(String searchName, Pageable pageable){
        return itemService.findAllMainItemDtoAndSearch(searchName, pageable);
    }
}
