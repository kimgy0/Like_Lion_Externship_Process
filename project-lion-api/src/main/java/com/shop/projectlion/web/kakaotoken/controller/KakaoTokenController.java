package com.shop.projectlion.web.kakaotoken.controller;

import com.shop.projectlion.web.kakaotoken.dto.KakaoTokenResponseDto;
import com.shop.projectlion.web.kakaotoken.service.KakaoTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoTokenController {

   private final KakaoTokenService kakaoTokenService;

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @Value("${kakao.client.id}")
    private String clientId;

    @GetMapping("/login/kakao")
    public String login(){
        return "loginForm";
    }

    @ResponseBody
    @GetMapping("/auth/kakao/callback")
    public String loginCallback(HttpServletRequest request,
                                @RequestParam(value = "code", required = false) String code){

        KakaoTokenResponseDto token = kakaoTokenService.getAccessToken(code, clientSecret, clientId, request);
        return "kakao token : " + token.toString() ;
    }
}