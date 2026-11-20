package com.dev.SentinelCore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "sentinel.security.jwt")
public record JwtProperties(String secret, long expirationMinutes) {}
