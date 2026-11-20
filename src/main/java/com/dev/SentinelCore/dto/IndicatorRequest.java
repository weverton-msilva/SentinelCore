package com.dev.SentinelCore.dto;

import com.dev.SentinelCore.enums.IndicatorType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IndicatorRequest(
        @NotBlank
        @Size(max = 512)
        String value,

        @NotNull
        IndicatorType type
) {}
