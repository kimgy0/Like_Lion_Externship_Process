package com.shop.projectlion.domain.member.repository;

import com.shop.projectlion.domain.member.entity.Member;
import com.shop.projectlion.domain.member.repository.projection.EmailOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.QueryHint;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Optional<EmailOnly> findProjectionByEmail(@Param("email") String email);
}
