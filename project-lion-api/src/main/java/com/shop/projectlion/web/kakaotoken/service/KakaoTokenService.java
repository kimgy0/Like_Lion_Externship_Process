package com.shop.projectlion.web.kakaotoken.service;

import com.shop.projectlion.global.constant.BaseConst;
import com.shop.projectlion.web.kakaotoken.dto.KakaoTokenResponseDto;
import com.shop.projectlion.web.kakaotoken.feign.KakaoTokenFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class KakaoTokenService {

    private final KakaoTokenFeignClient feignClient;

    @Transactional
    public KakaoTokenResponseDto getAccessToken(String code, String clientSecret, String clientId, HttpServletRequest request) {

        ConcurrentHashMap<String, String> map = setKakaoParameter(code, clientSecret, clientId, request);
        KakaoTokenResponseDto responseDto = feignClient.requestKakaoToken(map);
        return responseDto;
    }


    private ConcurrentHashMap<String, String> setKakaoParameter(String code, String clientSecret, String clientId, HttpServletRequest request) {
        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();
        map.put(BaseConst.GRANT_TYPE, BaseConst.KAKAO_FIX_GRANT_CODE);
        map.put(BaseConst.REDIRECT_URI, request.getRequestURL().toString());
        map.put(BaseConst.CODE, code);
        map.put(BaseConst.CLIENT_ID, clientId);
        map.put(BaseConst.CLIENT_SECRET, clientSecret);
        return map;
    }

}
