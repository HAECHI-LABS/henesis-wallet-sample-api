package io.haechi.sample.server.web;

import io.haechi.sample.server.domain.exceptions.ResourceNotFoundException;
import io.haechi.sample.server.infra.exceptions.FunctionCallException;
import io.haechi.sample.server.infra.exceptions.HenesisWalletException;
import io.haechi.sample.server.web.dto.ErrorBody;
import io.haechi.sample.server.web.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler({
            IllegalArgumentException.class,
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class,
            HttpMediaTypeNotSupportedException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalArgumentExceptionHandler(Exception e) {
        e.printStackTrace();
        return createErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({
            ResourceNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        e.printStackTrace();
        return createErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({
            Exception.class,
            HenesisWalletException.class,
            FunctionCallException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exceptionHandler(Exception e) {
        e.printStackTrace();
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    private ErrorResponse createErrorResponse(HttpStatus status, String responseMessage) {
        return ErrorResponse
                .builder()
                .error(
                        ErrorBody
                                .builder()
                                .code(status.value())
                                .message(responseMessage)
                                .build()
                )
                .build();
    }
}
