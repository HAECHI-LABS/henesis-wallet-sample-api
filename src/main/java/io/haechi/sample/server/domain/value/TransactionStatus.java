package io.haechi.sample.server.domain.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  TransactionStatus {
    REQUESTED("requested"),
    PENDING("pending"),
    FAILED("failed"),
    MINED("mined"),
    CONFIRMED("confirmed"),
    REPLACED("replaced");

    private String name;
}
