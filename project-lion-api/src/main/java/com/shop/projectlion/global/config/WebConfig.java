package com.shop.projectlion.global.config;

import com.shop.projectlion.global.interceptor.AuthenticationTokenInterceptor;
import com.shop.projectlion.global.interceptor.AuthorizationTokenInterceptor;
import com.shop.projectlion.web.kakaotoken.validator.TokenValidator;
import com.shop.projectlion.domain.jwt.service.TokenManager;
import com.shop.projectlion.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TokenManager tokenManager;
    private final TokenValidator tokenValidator;
    private final MemberService memberService;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.PATCH.name(),
                        HttpMethod.DELETE.name()
                );
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationTokenInterceptor(tokenManager, tokenValidator, memberService))
                .order(1)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/oauth/login","/api/health", "/api/oauth/login", "/api/logout", "/api/token");

        registry.addInterceptor(new AuthorizationTokenInterceptor(tokenManager))
                .order(2)
                .addPathPatterns("/api/admin/**");
    }

}