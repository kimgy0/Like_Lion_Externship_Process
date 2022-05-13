package com.shop.projectlion.web.kakaotoken.dto;

import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.validator.annotation.EnumPattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberTypeDto {

    @EnumPattern(regexp = "KAKAO|GENERAL", message = "KAKAO 또는 GENERAL 만 입력 가능합니다")
    private MemberType memberType;


}
