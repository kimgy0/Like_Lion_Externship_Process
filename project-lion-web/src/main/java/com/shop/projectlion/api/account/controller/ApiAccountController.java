package com.shop.projectlion.api.account.controller;

import com.shop.projectlion.api.account.service.AccountService;
import com.shop.projectlion.domain.jwt.dto.TokenDto;
import com.shop.projectlion.domain.jwt.dto.TokenRefreshDto;
import com.shop.projectlion.global.constant.BaseConst;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.web.kakaotoken.dto.MemberTypeDto;
import com.shop.projectlion.web.kakaotoken.validator.TokenValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiAccountController {

    private final TokenValidator tokenValidator;
    private final AccountService accountService;

    @PostMapping("/api/token")
    public TokenRefreshDto refreshToken(@RequestHeader(BaseConst.AUTHORIZATION_HEADER) String refreshToken) {

        tokenValidator.checkNotNullToken(refreshToken);
        tokenValidator.checkNormalAuthorizationType(refreshToken);

        return accountService.refreshAccessToken(refreshToken);
    }

    @PostMapping("/api/oauth/login")
    public TokenDto kakaoLogin(@Validated @RequestBody MemberTypeDto memberType,
                               @RequestHeader(BaseConst.AUTHORIZATION_HEADER) String accessToken) {

        tokenValidator.checkNotNullToken(accessToken);
        tokenValidator.checkNormalAuthorizationType(accessToken);

        if(memberType.getMemberType() == null){
            throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
        }
        TokenDto tokenDto = accountService.registerMember(accessToken, memberType.getMemberType());
        return tokenDto;
    }

    @PostMapping("/api/logout")
    public String expiredRefreshToken(@RequestHeader(BaseConst.AUTHORIZATION_HEADER) String accessToken) {
        accountService.expiredToken(accessToken);
        return "logout success";
    }
}
