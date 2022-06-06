package com.shop.projectlion.domain.member.exception;

import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;

public class TokenNotFoundException extends BusinessException {

    public TokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}