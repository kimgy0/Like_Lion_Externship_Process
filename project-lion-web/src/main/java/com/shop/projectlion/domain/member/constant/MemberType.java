package com.shop.projectlion.domain.member.constant;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum MemberType {
    GENERAL, KAKAO;


    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static MemberType findByString(String code) {
        return Stream.of(MemberType.values())
                .filter(t -> t.name().equals(code))
                .findFirst()
                .orElse(null);
    }
}
