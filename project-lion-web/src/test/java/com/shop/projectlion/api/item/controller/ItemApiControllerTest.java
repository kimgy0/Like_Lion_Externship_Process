package com.shop.projectlion.api.item.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.shop.projectlion.api.item.dto.ItemUpdateDto;
import com.shop.projectlion.api.item.service.ItemApiService;
import com.shop.projectlion.domain.item.constant.ItemSellStatus;
import com.shop.projectlion.global.error.GlobalExceptionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ItemApiControllerTest {

    @InjectMocks
    ItemApiController itemApiController;

    @Mock
    ItemApiService itemApiService;

    MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemApiController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    @Test
    @DisplayName("정상적인 상품 이름으로 업데이트시에 통과하는 테스트")
    public void 아이템_API_상품_업데이트_컨트롤러_테스트() throws Exception {

        String testURL = "/api/admin/items/8";

        ItemUpdateDto updateBeforeDto = toUpdateDto("상품 업데이트 할 이름");

        String requestJson = DtoToJson(updateBeforeDto);

        MvcResult mvcResult = mockMvc.perform(patch(testURL).contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(mvcResult.getResponse().getStatus(),HttpStatus.OK.value());
    }

    @Test
    @DisplayName("정상적이지 않은 상품 이름으로 업데이트시에 통과하는 테스트")
    public void 아이템_API_상품_업데이트_잘못된_전달_값_컨트롤러_테스트() throws Exception {

        String testURL = "/api/admin/items/8";

        ItemUpdateDto updateBeforeDto = toUpdateDto(null);

        String requestJson = DtoToJson(updateBeforeDto);


        MvcResult mvcResult = mockMvc.perform(patch(testURL).contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IllegalArgumentException))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(),status);
    }

    private String DtoToJson(ItemUpdateDto afterDto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(afterDto);
    }

    private ItemUpdateDto toUpdateDto(String productName) {
        return ItemUpdateDto.builder()
                .itemId(8L)
                .price(50000)
                .itemName(productName)
                .itemDetail("상품 업데이트 할 상세정보")
                .deliveryId(100000L)
                .stockNumber(10)
                .itemSellStatus(ItemSellStatus.SELL)
                .build();
    }
}