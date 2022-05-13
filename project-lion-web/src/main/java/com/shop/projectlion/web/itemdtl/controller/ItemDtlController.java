package com.shop.projectlion.web.itemdtl.controller;

import com.shop.projectlion.web.itemdtl.dto.ItemDtlDto;
import com.shop.projectlion.web.itemdtl.dto.ItemToOrderParameter;
import com.shop.projectlion.web.itemdtl.service.ItemDtlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/itemdtl")
@Slf4j
public class ItemDtlController {

    private final ItemDtlService itemDtlService;

    @GetMapping(value = "/{itemId}")
    public String itemDtl(Model model, @PathVariable("itemId") Long itemId){

        ItemDtlDto itemDtlDto = itemDtlService.itemDetailsInquire(itemId);
        model.addAttribute("item", itemDtlDto);

        return "itemdtl/itemdtl";
    }

    @PostMapping("/order")
    @ResponseBody
    public void order(@RequestBody ItemToOrderParameter parameter) throws IOException {
        itemDtlService.order(Long.parseLong(parameter.getItemId()), Integer.parseInt(parameter.getCount()));
    }
}
