package com.dev.SentinelCore.exception;

public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException() {
        super("Este e-mail já está cadastrado.");
    }
}
