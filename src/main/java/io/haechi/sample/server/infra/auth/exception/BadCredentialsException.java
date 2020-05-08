package io.haechi.sample.server.infra.auth.exception;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException(String msg) {
        super(msg);
    }
    public BadCredentialsException(String msg, Throwable t) {
        super(msg, t);
    }
}
