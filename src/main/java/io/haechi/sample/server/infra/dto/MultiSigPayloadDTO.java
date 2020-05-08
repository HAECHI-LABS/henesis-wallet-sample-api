package io.haechi.sample.server.infra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.haechi.sample.server.config.jackson.BigIntegerDeserializer;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MultiSigPayloadDTO {
    @JsonProperty("wallet_address")
    private String walletAddress;
    @JsonProperty("to_address")
    private String toAddress;
    @JsonDeserialize(using = BigIntegerDeserializer.class)
    private BigInteger value;
    @JsonDeserialize(using = BigIntegerDeserializer.class)
    @JsonProperty("wallet_nonce")
    private BigInteger walletNonce;
    private String data;
}
