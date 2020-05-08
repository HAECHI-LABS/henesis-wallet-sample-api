package io.haechi.sample.server.application.dto;

import io.haechi.sample.server.domain.value.TransactionStatus;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferEventDTO {
    private String transactionHash;
    private String from;
    private String to;
    private BigInteger amount;
    private TransactionStatus status;
    private String coinSymbol;
    private String createdAt;
}
