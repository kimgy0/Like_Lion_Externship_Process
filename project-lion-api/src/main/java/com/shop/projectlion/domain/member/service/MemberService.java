package com.shop.projectlion.domain.member.service;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.repository.MemberRepository;
import com.shop.projectlion.domain.member.validator.MemberValidator;
import com.shop.projectlion.global.error.exception.DuplicatedUserException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberValidator validator;

    public void createMember(Member member) throws DuplicatedUserException {
        validateRegisterMember(member);
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public void validateRegisterMember(Member member) throws DuplicatedUserException {
        validator.validateDuplicateMember(member.getEmail());
    }

    public Member findMemberWithEmail(String email){
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException(ErrorCode.NOT_FOUND_MEMBER.getMessage()));
    }
}
