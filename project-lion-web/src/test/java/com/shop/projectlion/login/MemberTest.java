package com.shop.projectlion.login;

import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.Role;
import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
@TestPropertySource(properties = "spring.config.location=classpath:application-test.yml")
class MemberTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 테스트")
    void register() {

        Member member = Member.builder()
                .email("test@naver.com")
                .username("김규영")
                .password(passwordEncoder.encode("password"))
                .refreshToken(null)
                .tokenExpirationTime(null)
                .role(Role.ROLE_ADMIN)
                .memberType(MemberType.GENERAL)
                .build();

        memberRepository.save(member);

        Member findMember = memberRepository.findByEmail("test@naver.com").get();
        Long id = findMember.getId();

        Assertions.assertThat(id).isEqualTo(memberRepository.findById(id).get().getId());
    }
}