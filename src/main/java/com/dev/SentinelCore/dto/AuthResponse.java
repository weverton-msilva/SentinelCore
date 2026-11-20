package com.dev.SentinelCore.dto;

public record AuthResponse(String accessToken, String tokenType, long expiresIn) {}