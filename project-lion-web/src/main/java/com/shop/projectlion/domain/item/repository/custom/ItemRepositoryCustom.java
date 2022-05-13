package com.shop.projectlion.domain.item.repository.custom;

import com.shop.projectlion.web.main.dto.MainItemDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<MainItemDto> findItemInfoAllCustom(String searchName , Pageable pageable);
}
