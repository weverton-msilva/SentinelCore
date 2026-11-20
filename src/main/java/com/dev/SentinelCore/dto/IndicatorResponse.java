package com.dev.SentinelCore.dto;

import com.dev.SentinelCore.entity.ThreatIndicator;
import com.dev.SentinelCore.enums.IndicatorType;

import java.time.Instant;

public record IndicatorResponse(Long id, String value, IndicatorType type, Instant createdAt) {

    public static IndicatorResponse from(ThreatIndicator e) {
        return new IndicatorResponse(e.getId(), e.getValue(), e.getType(), e.getCreatedAt());
    }
}
