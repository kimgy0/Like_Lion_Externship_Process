package com.shop.projectlion.api.item.service;

import com.shop.projectlion.api.item.dto.ItemUpdateDto;
import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.delivery.service.DeliveryService;
import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.domain.item.entitiy.Item;
import com.shop.projectlion.domain.item.service.ItemService;
import com.shop.projectlion.global.error.GlobalExceptionHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ItemApiServiceTest {

    @InjectMocks
    ItemApiService itemApiService;

    @Mock
    ItemService itemService;

    @Mock
    DeliveryService deliveryService;

    MockMvc mockMvc;

    Delivery payDelivery;
    Delivery freeDelivery;
    ItemUpdateDto updateBeforeDto;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemApiService)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @BeforeEach
    void createTestData() {
         payDelivery = Delivery.builder()
                .id(100000L)
                .fee(3000)
                .name("배송료 3000원")
                .build();

         freeDelivery = Delivery.builder()
                .id(100001L)
                .fee(0)
                .name("배송료 0원")
                .build();

        updateBeforeDto = ItemUpdateDto.builder()
                .itemId(8L)
                .price(50000)
                .itemName("상품 업데이트 할 상품 이름")
                .itemDetail("상품 업데이트 할 상세 정보")
                .deliveryId(100000L)
                .stockNumber(10)
                .itemSellStatus(ItemSellStatus.SELL)
                .build();
    }


    @Test
    @DisplayName("딜리버리가 교체되지 않아도 되는 업데이트 dto 를 보냈을 때 수행되어지는 테스트")
    public void 아이템_API_상품_업데이트_서비스_로직() {

        Long itemId = 8L;

        Item item = getFranckTestItemData(payDelivery);

        Mockito.when(itemService.findItemAndDeliveryEntity(itemId)).thenReturn(item);
        Mockito.when(deliveryService.findDeliveryById(100000L)).thenReturn(payDelivery);

        ItemUpdateDto updatedDto = itemApiService.updateItem(updateBeforeDto);

        Assertions.assertThat(updatedDto.getItemName()).isEqualTo(updateBeforeDto.getItemName());
        Assertions.assertThat(updatedDto.getItemSellStatus()).isEqualTo(updateBeforeDto.getItemSellStatus());
        Assertions.assertThat(updatedDto.getItemDetail()).isEqualTo(updateBeforeDto.getItemDetail());
        Assertions.assertThat(updatedDto.getPrice()).isEqualTo(updateBeforeDto.getPrice());
        Assertions.assertThat(updatedDto.getStockNumber()).isEqualTo(updateBeforeDto.getStockNumber());
    }

    @Test
    @DisplayName("딜리버리 ID 가 다른 값으로 들어왔을 때 수행되어지는 테스트")
    public void 아이템_API_딜리버리가_다를때_상품_업데이트_서비스_로직() {

        Long itemId = 8L;

        Item item = getFranckTestItemData(freeDelivery);

        Mockito.when(itemService.findItemAndDeliveryEntity(itemId)).thenReturn(item);
        Mockito.when(deliveryService.findDeliveryById(100000L)).thenReturn(payDelivery);

        ItemUpdateDto updatedDto = itemApiService.updateItem(updateBeforeDto);

        Assertions.assertThat(updatedDto.getItemName()).isEqualTo(updateBeforeDto.getItemName());
        Assertions.assertThat(updatedDto.getItemSellStatus()).isEqualTo(updateBeforeDto.getItemSellStatus());
        Assertions.assertThat(updatedDto.getItemDetail()).isEqualTo(updateBeforeDto.getItemDetail());
        Assertions.assertThat(updatedDto.getPrice()).isEqualTo(updateBeforeDto.getPrice());
        Assertions.assertThat(updatedDto.getStockNumber()).isEqualTo(updateBeforeDto.getStockNumber());
    }

    private Item getFranckTestItemData(Delivery delivery) {
        return Item.builder()
                .delivery(delivery)
                .id(8L)
                .name("상품 업데이트 전 상품 이름")
                .price(100000)
                .detail("상품 업데이트 전 상품 상세")
                .stock(100)
                .sellStatus(ItemSellStatus.SOLD_OUT)
                .build();
    }

}