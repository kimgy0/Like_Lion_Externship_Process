package com.shop.projectlion.web.adminitem.controller;

import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.adminitem.dto.DeliveryDto;
import com.shop.projectlion.web.adminitem.dto.InsertItemDto;
import com.shop.projectlion.web.adminitem.dto.ItemImageDto;
import com.shop.projectlion.web.adminitem.dto.UpdateItemDto;
import com.shop.projectlion.web.adminitem.service.AdminItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/item")
public class AdminItemController {

    private final AdminItemService itemService;

    private static final int FIRST_IMAGE_INDEX = 0;


    @ModelAttribute
    public void AutoInjectDelivery(Model model){
        List<DeliveryDto> deliveryDtoList = itemService.findDeliveryList();
        model.addAttribute("deliveryDtos",deliveryDtoList);
    }


    @GetMapping("/new")
    public String itemForm(Model model) {
        model.addAttribute("insertItemDto", new InsertItemDto());
        return "adminitem/registeritemform";
    }

    @PostMapping("/new")
    public String itemForm(@Validated @ModelAttribute InsertItemDto insertItemDto, BindingResult bindingResult) {

        MultipartFile multipartFile = insertItemDto.getItemImageFiles().get(FIRST_IMAGE_INDEX);
        if(multipartFile.isEmpty()){
            bindingResult.reject("firstImageNull", ErrorCode.NOT_FOUND_FIRST_IMAGE.getMessage());
        }

        if(bindingResult.hasErrors()){
            return "adminitem/registeritemform";
        }
        Long itemId = itemService.registerItem(insertItemDto);

        return "redirect:/admin/item/"+itemId;
    }


    @GetMapping("/{itemId}")
    public String itemEdit(@PathVariable Long itemId, Model model) {
        UpdateItemDto updateItem = new UpdateItemDto();
        try {
            updateItem = itemService.findUpdateItem(itemId);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "redirect:/";
        }

        model.addAttribute("updateItemDto", updateItem);
        return "adminitem/updateitemform";
    }



    @PostMapping("/{itemId}")
    public String itemEdit(@Validated @ModelAttribute UpdateItemDto updateItemDto,
                           BindingResult bindingResult,
                           @RequestParam List<Long> itemImageIds,
                           @PathVariable Long itemId) {


        preventNullOfUpdateDto(updateItemDto, itemImageIds);
        if(itemService.isFirstImageExist(updateItemDto, FIRST_IMAGE_INDEX)){
            bindingResult.reject("firstImageNull", ErrorCode.NOT_FOUND_FIRST_IMAGE.getMessage());
        }
        if(bindingResult.hasErrors()){
            return "adminitem/updateitemform";
        }

        try {
            itemService.UpdateItem(updateItemDto);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "redirect:/";
        }

        return "redirect:/admin/item/"+itemId;
    }

    private void preventNullOfUpdateDto(UpdateItemDto updateItemDto, List<Long> itemImageIds) {
        List<String> originalImageNames = updateItemDto.getOriginalImageNames();
        AtomicInteger count = new AtomicInteger(-1);
        for (String originalImageName : originalImageNames) {
            ItemImageDto itemImageDto = ItemImageDto
                    .builder()
                    .itemImageId(itemImageIds.get(count.incrementAndGet()))
                    .originalImageName(originalImageName)
                    .build();

            updateItemDto.getItemImageDtos().add(itemImageDto);
        }
    }
}