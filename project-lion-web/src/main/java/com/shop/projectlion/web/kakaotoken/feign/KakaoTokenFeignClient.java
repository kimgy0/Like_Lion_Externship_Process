package com.shop.projectlion.web.kakaotoken.feign;

import com.shop.projectlion.global.config.FeignConfiguration;
import com.shop.projectlion.web.kakaotoken.dto.KakaoTokenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "feign", url = "https://kauth.kakao.com", configuration = {FeignConfiguration.class})
public interface KakaoTokenFeignClient {

    @PostMapping(value = "/oauth/token" , consumes = MediaType.APPLICATION_JSON_VALUE)
    KakaoTokenResponseDto sendKakaoInfo(@SpringQueryMap Map<String, String> param);

}
