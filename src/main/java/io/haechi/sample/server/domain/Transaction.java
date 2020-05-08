package io.haechi.sample.server.domain;

import io.haechi.sample.server.domain.value.Address;
import io.haechi.sample.server.domain.value.TransactionHash;
import io.haechi.sample.server.domain.value.TransactionStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {
    private String id;
    private Address from;
    private Address to;
    private TransactionHash hash;
    private TransactionStatus status;
    private String error;
}
