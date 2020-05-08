package io.haechi.sample.server.infra.exceptions;

public class HenesisWalletException extends RuntimeException {
    public HenesisWalletException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
