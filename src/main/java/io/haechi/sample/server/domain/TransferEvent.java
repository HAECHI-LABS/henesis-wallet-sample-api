package io.haechi.sample.server.domain;

import io.haechi.sample.server.domain.value.TransactionStatus;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferEvent {
    private String transactionHash;
    private String from;
    private String to;
    private BigInteger amount;
    private TransactionStatus status;
    private String walletId;
    private String coinSymbol;
    private String transferType;
    private String createdAt;
}
