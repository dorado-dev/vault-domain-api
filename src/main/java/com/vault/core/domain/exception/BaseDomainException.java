package com.vault.core.domain.exception;

public abstract class BaseDomainException extends RuntimeException {
    protected BaseDomainException(String message) {
        super(message);
    }

}
