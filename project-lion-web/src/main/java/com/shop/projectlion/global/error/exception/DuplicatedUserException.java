package com.shop.projectlion.global.error.exception;

import lombok.Getter;

import java.sql.SQLException;

@Getter
public class DuplicatedUserException extends SQLException {

    public DuplicatedUserException(String reason) {
        super(reason);
    }
}
