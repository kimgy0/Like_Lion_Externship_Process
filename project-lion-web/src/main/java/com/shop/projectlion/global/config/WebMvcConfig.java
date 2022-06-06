package com.shop.projectlion.global.config;

import com.shop.projectlion.domain.jwt.service.TokenManager;
import com.shop.projectlion.domain.member.service.MemberService;
import com.shop.projectlion.api.interceptor.AuthenticationTokenInterceptor;
import com.shop.projectlion.api.interceptor.AuthorizationTokenInterceptor;
import com.shop.projectlion.web.kakaotoken.validator.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload.path}")
    private String uploadPath;

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
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadPath);
        /**
         *  리눅스의 경우 file :// 를 적어주고 + path 를 주지만,
         *  윈도우의 경우 D:/ 나 C:/ 부터 시작하니까 file: 만 적어주도록 한다.
         *
         *  /images/** url로 요청이 올 경우 uploadPath 로부터 파일을 찾음
         **/
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