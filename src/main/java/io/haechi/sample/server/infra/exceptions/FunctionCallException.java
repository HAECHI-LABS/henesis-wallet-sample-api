package io.haechi.sample.server.infra.exceptions;

public class FunctionCallException extends RuntimeException {
    public FunctionCallException(Throwable throwable) {
        super(throwable);
    }
}
