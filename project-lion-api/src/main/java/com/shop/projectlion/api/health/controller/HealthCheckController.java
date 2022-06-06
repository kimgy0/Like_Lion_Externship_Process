package com.shop.projectlion.api.health.controller;

import com.shop.projectlion.api.health.constant.HealthType;
import com.shop.projectlion.api.health.dto.HealthStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {


    @GetMapping("/api/health")
    public ResponseEntity<HealthStatusDto> checkServer(){
        try {
            return new ResponseEntity(HealthStatusDto.builder()
                    .health(HealthType.ok)
                    .status(true)
                    .build(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HealthStatusDto.builder()
                                        .health(HealthType.no)
                                        .status(false)
                                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
