package com.shop.projectlion.api.login.feign;

import com.shop.projectlion.api.login.dto.KakaoUserInfo;
import com.shop.projectlion.global.config.FeignConfiguration;
import com.shop.projectlion.global.constant.BaseConst;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "feignGet", url = "https://kapi.kakao.com", configuration = {FeignConfiguration.class})
public interface KakaoGetInfoFeignClient {
    @PostMapping(value = "/v2/user/me" , consumes = MediaType.APPLICATION_JSON_VALUE)
    KakaoUserInfo getKakaoUserInfo(@RequestHeader(BaseConst.AUTHORIZATION_OF_HEADER_NAME) String accessToken);
}
