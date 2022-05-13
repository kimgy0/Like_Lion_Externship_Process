package com.shop.projectlion.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 인증
    ALREADY_REGISTERED_MEMBER(400, "이미 가입된 회원 입니다."),
    MISMATCHED_PASSWORD(401, "패스워드가 일치하지 않습니다."),
    LOGIN_ERROR(401, "아이디 또는 비밀번호를 확인해주세요"),

    INVALID_MEMBER_TYPE(400, "잘못된 회원 타입 입니다.(memberType : KAKAO)"),
    NOT_EXISTS_AUTHORIZATION(401, "Authorization Header가 빈값입니다."),
    NOT_VALID_BEARER_GRANT_TYPE(401, "인증 타입이 Bearer 타입이 아닙니다."),
    NOT_VALID_TOKEN(401, "유효하지않은 토큰 입니다."),
    ACCESS_TOKEN_EXPIRED(401, "해당 access token은 만료됐습니다."),
    NOT_ACCESS_TOKEN_TYPE(401, "tokenType이 access token이 아닙니다."),
    NOT_REFRESH_TOKEN_TYPE(401, "tokenType이 refresh token이 아닙니다."),
    REFRESH_TOKEN_EXPIRED(401, "해당 refresh token은 만료됐습니다."),
    REFRESH_TOKEN_NOT_FOUND(400, "해당 refresh token은 존재하지 않습니다."),
    CANCEL_LOGIN_OAUTH(401, "로그인이 취소되었거나 정상적인 요청이 아닙니다."),

    NOT_FOUND_MEMBER(401, "이메일이 존재하지 않습니다."),
    NOT_FOUND_DELIVERY(401, "배달 정보가 존재하지 않습니다."),
    NOT_FOUND_FIRST_IMAGE(401,"첫번째 상품 이미지는 필수 입니다."),
    NOT_FOUND_ITEM(401,"해당 아이템이 존재하지 않습니다."),
    OUT_OF_STOCK_BOUND(401, "아이템의 재고가 0보다 낮습니다."),
    NOT_FOUND_ORDER(401, "주문이 유효하지 않습니다.")
    ;



    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private int status;
    private String message;

}