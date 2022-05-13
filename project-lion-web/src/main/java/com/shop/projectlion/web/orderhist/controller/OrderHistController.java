package com.shop.projectlion.web.orderhist.controller;

import com.shop.projectlion.global.constant.BaseConst;
import com.shop.projectlion.web.orderhist.dto.OrderHistDto;
import com.shop.projectlion.web.orderhist.service.OrderHistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orderhist")
@Slf4j
public class OrderHistController {

    private final OrderHistService orderHistService;

    @GetMapping
    public String orderHist(Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : BaseConst.SET_PAGE_DEFAULT_COUNT, BaseConst.SET_PAGE_ITEM_MAX_COUNT);

        Page<OrderHistDto> pageOrderHistDtos = orderHistService.orderHistPagePresent(pageable);

        model.addAttribute("orders", pageOrderHistDtos);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", BaseConst.SET_PAGE_MAX_COUNT); // 메인페이지에 노출되는 최대 페이지 갯수
        return "orderhist/orderhist";
    }


    @PostMapping("/{orderId}/cancel")
    public String orderCancel(@PathVariable("orderId") Long orderId) {

        orderHistService.orderCancelFunction(orderId);
        return "redirect:/orderhist";
    }


}
