package com.shop.projectlion.domain.member.entity;

import com.shop.projectlion.api.account.dto.KakaoUserInfo;
import com.shop.projectlion.domain.common.BaseEntity;
import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.item.entitiy.Item;
import com.shop.projectlion.domain.jwt.dto.TokenDto;
import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.Role;
import com.shop.projectlion.global.util.DateTimeUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10, name = "member_type")
    private MemberType memberType;

    @Column(unique = true, length = 50, nullable = false, name = "email")
    private String email;

    @Column(length = 200, name = "password")
    private String password;

    @Column(nullable = false, length = 20, name = "member_name")
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20, name = "role")
    private Role role;

    @Column(length = 250,name = "refresh_token")
    private String refreshToken;

    @Column(name = "token_expiration_time")
    private LocalDateTime tokenExpirationTime;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Delivery> deliveries = new ArrayList<>();


    @Builder
    public Member(MemberType memberType, String email, String password, String username, Role role, String refreshToken, LocalDateTime tokenExpirationTime, LocalDateTime accessTokenExpiredTime) {
        this.memberType = memberType;
        this.email = email;
        this.password = password;
        this.username = username;
        this.role = role;
        this.refreshToken = refreshToken;
        this.tokenExpirationTime = tokenExpirationTime;
        this.accessTokenExpiredTime = accessTokenExpiredTime;
    }

    private LocalDateTime accessTokenExpiredTime;

    public static Member kakaoToEntity(KakaoUserInfo user, MemberType memberType, TokenDto tokenDto) {
        return Member.builder()
                .email(user.getKakaoAccount().getEmail())
                .username(user.getProperties().get("nickname"))
                .password(null)
                .refreshToken(tokenDto.getRefreshToken())
                .tokenExpirationTime(DateTimeUtils.convertToLocalDateTime(tokenDto.getRefreshTokenExpireTime()))
                .role(Role.ROLE_USER)
                .memberType(memberType)
                .accessTokenExpiredTime(DateTimeUtils.convertToLocalDateTime(tokenDto.getAccessTokenExpireTime()))
                .build();
    }

    public void updateRefreshToken(String refreshToken, Date refreshTokenExpireTime, Date accessTokeExpiredTime) {
        this.refreshToken = refreshToken;
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(refreshTokenExpireTime);
        this.accessTokenExpiredTime = DateTimeUtils.convertToLocalDateTime(accessTokeExpiredTime);
    }

    public void expiredRefreshToken(){
        this.refreshToken = null;
        this.tokenExpirationTime = null;
        this.accessTokenExpiredTime = null;
    }

    public void updateAccessToken(LocalDateTime date){
        this.accessTokenExpiredTime = date;
    }

}