package com.shop.projectlion.api.item.controller;

import com.shop.projectlion.api.item.dto.ItemApiDto;
import com.shop.projectlion.api.item.dto.ItemUpdateDto;
import com.shop.projectlion.api.item.service.ItemApiService;
import com.shop.projectlion.global.constant.BaseConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/items")
@Api(tags = {"상품 업데이트를 진행합니다."})
public class ItemApiController {

    private final ItemApiService itemApiService;

    @GetMapping("/{itemId}")
    public ItemApiDto inquiryItem(@PathVariable Long itemId){
        return itemApiService.inquiryItem(itemId);
    }

    @ApiResponses({@ApiResponse(code = 200, message = "OK", response = ItemUpdateDto.class)})
    @ApiOperation(value = "updateItem" , notes = "updateItem API")
    @PatchMapping("/{itemId}")
    public ItemUpdateDto updateItem(@Validated @RequestBody ItemUpdateDto itemUpdateRequestDto,
                                    BindingResult bindingResult){
        log.info("reset");
        if(bindingResult.hasErrors()){
            throw new IllegalArgumentException(bindingResult.getAllErrors().get(BaseConst.FIRST_ENTITY).getDefaultMessage());
        }
        return itemApiService.updateItem(itemUpdateRequestDto);
    }
}
