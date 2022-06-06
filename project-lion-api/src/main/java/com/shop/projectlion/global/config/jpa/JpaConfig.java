package com.shop.projectlion.global.config.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorProvider(HttpServletRequest request) {
        return new AuditorAwareImpl(request);
    }

}
