package com.dev.SentinelCore.service;

import com.dev.SentinelCore.config.JwtProperties;
import com.dev.SentinelCore.dto.RegisterRequest;
import com.dev.SentinelCore.dto.UserResponse;
import com.dev.SentinelCore.dto.AuthResponse;
import com.dev.SentinelCore.dto.LoginRequest;
import com.dev.SentinelCore.entity.User;
import com.dev.SentinelCore.enums.Role;
import com.dev.SentinelCore.exception.EmailAlreadyUsedException;
import com.dev.SentinelCore.repository.UserRepository;
import com.dev.SentinelCore.security.TokenService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final JwtProperties jwtProperties;

    @Transactional
    public UserResponse register(RegisterRequest request) {

        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyUsedException();
        }

        User user = new User();

        user.setName(request.name().trim());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.ANALYST);

        return UserResponse.from(userRepository.save(user));
    }

    public AuthResponse login(LoginRequest request) {

        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                email,
                                request.password()
                        )
                );

        String token = tokenService.generate(authentication);

        return new AuthResponse(
                token,
                "Bearer",
                jwtProperties.expirationMinutes() * 60
        );
    }
}
