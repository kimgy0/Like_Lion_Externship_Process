package com.shop.projectlion.web.kakaotoken.service;

import com.shop.projectlion.api.login.dto.KakaoUserInfo;
import com.shop.projectlion.api.login.feign.KakaoGetInfoFeignClient;
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
import com.shop.projectlion.global.constant.BaseConst;
import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.DuplicatedUserException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.global.util.DateTimeUtils;
import com.shop.projectlion.web.kakaotoken.dto.KakaoTokenResponseDto;
import com.shop.projectlion.web.kakaotoken.feign.KakaoTokenFeignClient;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class KakaoTokenService {

    private final KakaoTokenFeignClient feignClient;
    private final MemberService memberService;
    private final KakaoGetInfoFeignClient getInfoFeignClient;
    private final TokenManager tokenManager;

    @Transactional
    public KakaoTokenResponseDto getAccessToken(String code, String clientSecret, String clientId, HttpServletRequest request) {

        ConcurrentHashMap<String, String> map = setKakaoParameter(code, clientSecret, clientId, request);
        KakaoTokenResponseDto responseDto = feignClient.sendKakaoInfo(map);
        return responseDto;
    }

    @Transactional
    public TokenDto registerMember(String accessToken, MemberType memberType) {

        KakaoUserInfo userInfo = getInfoFeignClient.getKakaoUserInfo(accessToken);

        String email = userInfo.getKakaoAccount().getEmail();
        TokenDto tokenDto = tokenManager.createTokenDto(email, Role.ROLE_USER);
        try {
            kakaoRegister(memberType, userInfo, tokenDto);

        } catch (DuplicatedUserException e){
            updateAlreadyMemberRefreshToken(email, tokenDto);
        }
        return tokenDto;
    }

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

    private void updateAlreadyMemberRefreshToken(String email, TokenDto tokenDto) {
        Member member = memberService.findMemberWithEmail(email);
        if(member.getMemberType() == MemberType.KAKAO){
            member.updateRefreshToken(  tokenDto.getRefreshToken(),
                                        tokenDto.getRefreshTokenExpireTime(),
                                        tokenDto.getAccessTokenExpireTime());
                            }
    }

    private void kakaoRegister(MemberType memberType, KakaoUserInfo userInfo, TokenDto tokenDto) throws DuplicatedUserException {
        Member member = Member.kakaoToEntity(userInfo, memberType, tokenDto);
        memberService.createMember(member);
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
