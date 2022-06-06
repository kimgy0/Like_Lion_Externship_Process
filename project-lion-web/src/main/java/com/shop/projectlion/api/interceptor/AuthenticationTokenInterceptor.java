package com.shop.projectlion.api.interceptor;

import com.shop.projectlion.domain.jwt.constant.GrantType;
import com.shop.projectlion.domain.jwt.constant.TokenType;
import com.shop.projectlion.domain.jwt.service.TokenManager;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.exception.NotValidTokenException;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.global.constant.BaseConst;
import com.shop.projectlion.global.error.exception.ErrorCode;
import com.shop.projectlion.global.util.DateTimeUtils;
import com.shop.projectlion.web.kakaotoken.validator.TokenValidator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RequiredArgsConstructor
@Slf4j
public class AuthenticationTokenInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;
    private final TokenValidator tokenValidator;
    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwtHeader = request.getHeader(BaseConst.AUTHORIZATION_HEADER);

        if(tokenValidator.checkNotNullToken(jwtHeader) && tokenValidator.checkNormalAuthorizationType(jwtHeader)){

            String jwt = jwtHeader.replace(GrantType.BEARRER.getType() + " ", "");
            Claims tokenClaims = tokenManager.getTokenClaims(jwt);

            if(tokenManager.isTokenValid(jwt)) {
                if(tokenClaims.getSubject().equals(TokenType.ACCESS.name())) {
                    String email = tokenManager.getMemberEmail(jwt);
                    Member member = memberService.findMemberWithEmail(email);

                    Date expiration = tokenClaims.getExpiration();
                    Date memberExpiration = DateTimeUtils.convertToDate(member.getAccessTokenExpiredTime());
                    if(!expiration.toString().equals(memberExpiration.toString())){
                        throw new NotValidTokenException(ErrorCode.ACCESS_TOKEN_EXPIRED);
                    }
                } else {
                    throw new NotValidTokenException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
                }
            }else{
                throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);
            }
            request.setAttribute(BaseConst.ACCESS_TOKEN, jwt);
        }
        return true;

    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}