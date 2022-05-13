package com.shop.projectlion.web.main.controller;

import com.shop.projectlion.global.constant.BaseConst;
import com.shop.projectlion.web.main.dto.ItemSearchDto;
import com.shop.projectlion.web.main.dto.MainItemDto;
import com.shop.projectlion.web.main.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final MainService mainService;

    @GetMapping("/")
    public String mainPage(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {

        String searchQuery = itemSearchDto.getSearchQuery();
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : BaseConst.SET_PAGE_DEFAULT_COUNT, BaseConst.SET_PAGE_ITEM_MAX_COUNT);
        
        Page<MainItemDto> pageMainItemDto = mainService.mainPagePresent(searchQuery.isBlank() ? null : searchQuery, pageable);

        model.addAttribute("items", pageMainItemDto);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", BaseConst.SET_PAGE_MAX_COUNT);

        return "main/mainpage";
    }

}