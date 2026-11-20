package com.dev.SentinelCore.dto;

import com.dev.SentinelCore.entity.User;
import com.dev.SentinelCore.enums.Role;

public record UserResponse(Long id, String name, String email, Role role) {
    public static UserResponse from(User u) {
        return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole());
    }
}
