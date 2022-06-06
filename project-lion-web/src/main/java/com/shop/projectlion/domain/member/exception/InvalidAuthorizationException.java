package com.shop.projectlion.domain.member.exception;

import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;

public class InvalidAuthorizationException extends BusinessException {

    public InvalidAuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }

}
