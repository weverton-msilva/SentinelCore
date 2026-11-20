package com.dev.SentinelCore.security;

import com.dev.SentinelCore.entity.User;
import com.dev.SentinelCore.enums.Role;
import com.dev.SentinelCore.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminBootstrap implements ApplicationRunner {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Value("${sentinel.admin.email:}")
    private String adminEmail;

    @Value("${sentinel.admin.password:}")
    private String adminPassword;

    @Override
    public void run(ApplicationArguments args) {
        if (adminEmail.isBlank() || adminPassword.isBlank()) {
            log.warn("ADMIN_EMAIL/ADMIN_PASSWORD não definidos: nenhum admin inicial criado.");
            return;
        }
        String email = adminEmail.trim().toLowerCase(Locale.ROOT);
        if (repository.existsByEmail(email)) {
            return;
        }
        User admin = new User();
        admin.setName("Administrador");
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(adminPassword));
        admin.setRole(Role.ADMIN);
        repository.save(admin);
        log.info("Administrador inicial criado: {}", email);
    }
}
