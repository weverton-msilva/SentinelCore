package com.dev.SentinelCore.exception;

import com.dev.SentinelCore.enums.IndicatorType;

public class DuplicateIndicatorException extends RuntimeException {
    public DuplicateIndicatorException(String value, IndicatorType type) {
        super("Indicador já cadastrado: " + type + " " + value);
    }
}
