package com.shop.projectlion.global.security.service;

import com.shop.projectlion.domain.member.entity.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


@Getter
public class FormLoginUserDetails extends User {

    private Member member;

    public FormLoginUserDetails(Member member, Collection<? extends GrantedAuthority> authorities) {
        super(member.getUsername(), member.getPassword(), authorities);
        this.member = member;
    }
}
