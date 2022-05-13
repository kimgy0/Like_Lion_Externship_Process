package com.shop.projectlion.global.config;

import com.shop.projectlion.domain.member.entity.Member;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Configuration
@ComponentScan
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider(HttpServletRequest request){
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication instanceof AnonymousAuthenticationToken){
                return Optional.of(request.getRequestURI());
            }
            return Optional.of(((Member) authentication.getPrincipal()).getEmail());
        };
    }
}
