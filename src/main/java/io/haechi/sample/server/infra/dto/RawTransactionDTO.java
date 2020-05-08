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
public class RawTransactionDTO {
    @JsonDeserialize(using = BigIntegerDeserializer.class)
    private BigInteger nonce;
    @JsonDeserialize(using = BigIntegerDeserializer.class)
    @JsonProperty("gas_price")
    private BigInteger gasPrice;
    @JsonDeserialize(using = BigIntegerDeserializer.class)
    @JsonProperty("gas_limit")
    private BigInteger gasLimit;
    private String to;
    @JsonDeserialize(using = BigIntegerDeserializer.class)
    private BigInteger value;
    private String data;
}
