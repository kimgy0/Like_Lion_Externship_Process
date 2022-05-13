package com.shop.projectlion.api.health.controller;

import com.shop.projectlion.api.health.constant.HealthType;
import com.shop.projectlion.api.health.dto.HealthStatusDto;
import com.shop.projectlion.api.health.feign.HealthFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    private final HealthFeignClient healthFeignClient;

    @GetMapping("/api/health")
    public ResponseEntity<HealthStatusDto> checkServer(){
        ResponseEntity<HealthStatusDto> serverStatus;
        try {
            serverStatus = healthFeignClient.getServerStatus();
        }catch (Exception e){
            return new ResponseEntity(HealthStatusDto.builder()
                                        .health(HealthType.no)
                                        .status(false)
                                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return serverStatus;
    }

    @GetMapping("/api/health-check")
    protected ResponseEntity<HealthStatusDto> clientMethod(){
        return new ResponseEntity(HealthStatusDto.builder()
                                    .health(HealthType.ok)
                                    .status(true)
                                    .build(),HttpStatus.OK);
    }



}
