package com.dev.SentinelCore.exception;

public class IndicatorNotFoundException extends RuntimeException {
    public IndicatorNotFoundException(Long id) {
        super("Indicador não encontrado: " + id);
    }
}