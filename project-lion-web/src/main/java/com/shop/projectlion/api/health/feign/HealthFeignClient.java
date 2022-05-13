package com.shop.projectlion.api.health.feign;

import com.shop.projectlion.api.health.dto.HealthStatusDto;
import com.shop.projectlion.global.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "http://localhost:8081", name = "healthFeignClient",configuration = {FeignConfiguration.class})
public interface HealthFeignClient {
    @GetMapping(value = "/api/health-check", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<HealthStatusDto> getServerStatus();
}
