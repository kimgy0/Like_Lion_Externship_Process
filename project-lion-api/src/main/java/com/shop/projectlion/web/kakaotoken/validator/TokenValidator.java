package com.shop.projectlion.web.kakaotoken.validator;

import com.shop.projectlion.domain.jwt.constant.GrantType;
import com.shop.projectlion.domain.member.exception.NotValidTokenException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class TokenValidator {

    public boolean checkNotNullToken(String accessToken) {
        if(accessToken == null || accessToken.isBlank() || accessToken.isEmpty()){
            throw new NotValidTokenException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }else{
            return true;
        }
    }

    public boolean checkNormalAuthorizationType(String accessToken){
        if(!accessToken.startsWith(GrantType.BEARRER.getType()+" ")){
            throw new NotValidTokenException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }else{
            return true;
        }
    }
}
