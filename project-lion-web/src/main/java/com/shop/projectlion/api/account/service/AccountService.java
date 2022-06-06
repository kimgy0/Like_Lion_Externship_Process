package com.shop.projectlion.api.account.service;


import com.shop.projectlion.api.account.dto.KakaoUserInfo;
import com.shop.projectlion.api.account.feign.KakaoUserInfoClient;
import com.shop.projectlion.domain.jwt.constant.GrantType;
import com.shop.projectlion.domain.jwt.constant.TokenType;
import com.shop.projectlion.domain.jwt.dto.TokenDto;
import com.shop.projectlion.domain.jwt.dto.TokenRefreshDto;
import com.shop.projectlion.domain.jwt.service.TokenManager;
import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.Role;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.exception.NotValidTokenException;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.DuplicatedUserException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.global.util.DateTimeUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountService {

    private final TokenManager tokenManager;
    private final MemberService memberService;
    private final KakaoUserInfoClient kakaoUserInfoClient;

    @Transactional
    public TokenRefreshDto refreshAccessToken(String refreshToken) {

        String notTypeToken = refreshToken.replace(GrantType.BEARRER.getType() + " ","");
        Claims tokenClaims = tokenManager.getTokenClaims(notTypeToken);

        if(tokenManager.isTokenValid(notTypeToken) && tokenClaims.getSubject().equals(TokenType.REFRESH.name())){
            String email = tokenManager.getMemberEmail(notTypeToken);
            Member member = memberService.findMemberWithEmail(email);
            if(tokenManager.isMemberRefreshTokenValid(notTypeToken, member)){
                TokenRefreshDto accessToken = tokenManager.RefreshTokenDto(email, member.getRole());
                member.updateAccessToken(DateTimeUtils.convertToLocalDateTime(accessToken.getAccessTokenExpireTime()));
                return accessToken;
            }
        }else{
            throw new BusinessException(ErrorCode.NOT_REFRESH_TOKEN_TYPE);
        }
        return new TokenRefreshDto();
    }

    @Transactional
    public TokenDto registerMember(String accessToken, MemberType memberType) {

        KakaoUserInfo userInfo = kakaoUserInfoClient.getKakaoUserInfo(accessToken);

        String email = userInfo.getKakaoAccount().getEmail();
        TokenDto tokenDto = tokenManager.createTokenDto(email, Role.ROLE_USER);
        try {
            kakaoRegister(memberType, userInfo, tokenDto);
        } catch (DuplicatedUserException e){
            return updateAlreadyMemberRefreshToken(email);
        }
        return tokenDto;
    }

    @Transactional
    public void expiredToken(String accessToken) {
        String notTypeToken = accessToken.replace(GrantType.BEARRER.getType() + " ","");
        Claims tokenClaims = tokenManager.getTokenClaims(notTypeToken);

        if(tokenManager.isTokenValid(notTypeToken) && tokenClaims.getSubject().equals(TokenType.ACCESS.name())) {
            String email = tokenManager.getMemberEmail(notTypeToken);
            Member member = memberService.findMemberWithEmail(email);

            Date expiration = tokenClaims.getExpiration();
            Date memberExpiration = DateTimeUtils.convertToDate(member.getAccessTokenExpiredTime());

            if(expiration.toString().equals(memberExpiration.toString())){
                member.expiredRefreshToken();
            }else{
                throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);
            }
        }else{
            throw new NotValidTokenException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }
    }

    private TokenDto updateAlreadyMemberRefreshToken(String email) {
        Member member = memberService.findMemberWithEmail(email);
        TokenDto tokenDto = tokenManager.createTokenDto(email, member.getRole());
        if(member.getMemberType() == MemberType.KAKAO){
            member.updateRefreshToken(  tokenDto.getRefreshToken(),
                    tokenDto.getRefreshTokenExpireTime(),
                    tokenDto.getAccessTokenExpireTime());
        }
        return tokenDto;
    }

    private void kakaoRegister(MemberType memberType, KakaoUserInfo userInfo, TokenDto tokenDto) throws DuplicatedUserException {
        Member member = Member.kakaoToEntity(userInfo, memberType, tokenDto);
        memberService.createMember(member);
    }

}
