package com.shop.projectlion.domain.member.exception;

import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.global.error.exception.ErrorCode;

public class NotValidTokenException extends BusinessException {

    public NotValidTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

}