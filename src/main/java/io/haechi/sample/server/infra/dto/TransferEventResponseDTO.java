package io.haechi.sample.server.infra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.haechi.sample.server.config.jackson.BigIntegerDeserializer;
import io.haechi.sample.server.config.jackson.BigIntegerSerializer;
import io.haechi.sample.server.domain.value.TransactionStatus;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferEventResponseDTO {
    @JsonProperty("transaction_hash")
    private String transactionHash;
    private String from;
    private String to;
    @JsonSerialize(using = BigIntegerSerializer.class)
    @JsonDeserialize(using = BigIntegerDeserializer.class)
    private BigInteger amount;
    private TransactionStatus status;
    @JsonProperty("wallet_id")
    private String walletId;
    @JsonProperty("coin_symbol")
    private String coinSymbol;
    @JsonProperty("transfer_type")
    private String transferType;
    @JsonProperty("created_at")
    private String createdAt;
}
