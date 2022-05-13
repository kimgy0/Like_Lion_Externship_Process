package com.shop.projectlion.web.kakaotoken.controller;

import com.shop.projectlion.domain.jwt.dto.TokenDto;
import com.shop.projectlion.domain.jwt.dto.TokenRefreshDto;
import com.shop.projectlion.global.constant.BaseConst;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.kakaotoken.dto.KakaoTokenResponseDto;
import com.shop.projectlion.web.kakaotoken.dto.MemberTypeDto;
import com.shop.projectlion.web.kakaotoken.service.KakaoTokenService;
import com.shop.projectlion.web.kakaotoken.validator.TokenValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class KakaoTokenController {

   private final KakaoTokenService kakaoTokenService;
   private final TokenValidator tokenValidator;

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

    @ResponseBody
    @PostMapping("/api/token")
    public TokenRefreshDto refreshToken(@RequestHeader(BaseConst.AUTHORIZATION_OF_HEADER_NAME) String refreshToken) {

        tokenValidator.checkNotNullToken(refreshToken);
        tokenValidator.checkNormalAuthorizationType(refreshToken);

        return kakaoTokenService.refreshAccessToken(refreshToken);
    }

    @ResponseBody
    @PostMapping("/api/oauth/login")
    public TokenDto kakaoLogin(@Validated @RequestBody MemberTypeDto memberType,
                             @RequestHeader(BaseConst.AUTHORIZATION_OF_HEADER_NAME) String accessToken) {

        tokenValidator.checkNotNullToken(accessToken);
        tokenValidator.checkNormalAuthorizationType(accessToken);

        if(memberType.getMemberType() == null){
            throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
        }
        TokenDto tokenDto = kakaoTokenService.registerMember(accessToken, memberType.getMemberType());
        return tokenDto;
    }

    @ResponseBody
    @PostMapping("/api/logout")
    public String expiredRefreshToken(@RequestHeader(BaseConst.AUTHORIZATION_OF_HEADER_NAME) String refreshToken) {
        kakaoTokenService.expiredToken(refreshToken);
        return "logout success";
    }
}