package com.shop.projectlion.domain.member.entity;

import com.shop.projectlion.api.login.dto.KakaoUserInfo;
import com.shop.projectlion.domain.delivery.entity.Delivery;
import com.shop.projectlion.domain.item.entitiy.Item;
import com.shop.projectlion.domain.jwt.dto.TokenDto;
import com.shop.projectlion.domain.member.constant.MemberType;
import com.shop.projectlion.domain.member.constant.Role;
import com.shop.projectlion.domain.orders.entity.Order;
import com.shop.projectlion.domain.sub.CommonSubEntity;
import com.shop.projectlion.global.util.DateTimeUtils;
import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends CommonSubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "member_type")
    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "token_expiration_time")
    private LocalDateTime tokenExpirationTime;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime accessTokenExpiredTime;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Delivery> deliveries = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public static Member toEntity(MemberRegisterDto memberRegisterDto, PasswordEncoder passwordEncoder){
        return Member.builder()
                .email(memberRegisterDto.getEmail())
                .username(memberRegisterDto.getName())
                .password(passwordEncoder.encode(memberRegisterDto.getPassword()))
                .refreshToken(null)
                .tokenExpirationTime(null)
                .memberType(MemberType.GENERAL)
                .role(Role.ROLE_ADMIN)
                .build();
    }

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