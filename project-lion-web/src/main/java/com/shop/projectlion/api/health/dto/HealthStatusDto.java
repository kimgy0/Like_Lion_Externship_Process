package com.shop.projectlion.api.health.dto;

import com.shop.projectlion.api.health.constant.HealthType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class HealthStatusDto {
    private boolean status;
    private HealthType health;

    @Builder
    public HealthStatusDto(boolean status, HealthType health) {
        this.status = status;
        this.health = health;
    }
}
