package com.dev.SentinelCore.service;

import com.dev.SentinelCore.enums.IndicatorType;
import com.dev.SentinelCore.exception.InvalidIndicatorException;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.regex.Pattern;

@Component
public class IndicatorValidator {

    private static final Pattern DOMAIN = Pattern.compile(
            "^(?=.{1,253}$)([a-z0-9]([a-z0-9-]{0,61}[a-z0-9])?\\.)+[a-z]{2,63}$");

    private static final Pattern HASH = Pattern.compile(
            "^([a-f0-9]{32}|[a-f0-9]{40}|[a-f0-9]{64})$"); // MD5, SHA-1, SHA-256

    public String normalizeAndValidate(String rawValue, IndicatorType type) {
        String value = rawValue.trim().toLowerCase(Locale.ROOT);

        boolean valid = switch (type) {
            case IP -> isIpv4(value) || isIpv6(value);
            case DOMAIN -> DOMAIN.matcher(value).matches();
            case HASH -> HASH.matcher(value).matches();
        };

        if (!valid) {
            throw new InvalidIndicatorException("Valor inválido para o tipo " + type);
        }
        return value;
    }

    private boolean isIpv4(String value) {
        String[] parts = value.split("\\.", -1);
        if (parts.length != 4) return false;
        for (String part : parts) {
            if (!part.matches("\\d{1,3}") || Integer.parseInt(part) > 255) return false;
        }
        return true;
    }

    private boolean isIpv6(String value) {
        // Só chega no parser se tiver ":" e apenas caracteres de IP.
        if (!value.contains(":") || !value.matches("[0-9a-f:.]+")) return false;
        try {
            InetAddress.getByName(value);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
