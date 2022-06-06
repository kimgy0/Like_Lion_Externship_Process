package com.shop.projectlion.domain.member.validator;

import com.shop.projectlion.domain.member.repository.MemberRepository;
import com.shop.projectlion.global.error.exception.DuplicatedUserException;
import com.shop.projectlion.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;

    public void validateDuplicateMember(String email) throws DuplicatedUserException {
        if(memberRepository.findProjectionByEmail(email).isPresent()){
            throw new DuplicatedUserException(ErrorCode.ALREADY_REGISTERED_MEMBER.getMessage());
        }
    }

}
