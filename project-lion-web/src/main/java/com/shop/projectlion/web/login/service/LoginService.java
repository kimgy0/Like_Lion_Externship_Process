package com.shop.projectlion.web.login.service;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.global.error.exception.DuplicatedUserException;
import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;


    @Transactional
    public void registerMember(MemberRegisterDto memberRegisterDto) throws DuplicatedUserException {
        Member member = Member.toEntity(memberRegisterDto,passwordEncoder);
        memberService.createMember(member);
    }
}
