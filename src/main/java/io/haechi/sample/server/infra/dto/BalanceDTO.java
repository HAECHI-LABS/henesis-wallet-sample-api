package io.haechi.sample.server.infra.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.haechi.sample.server.config.jackson.BigIntegerDeserializer;
import io.haechi.sample.server.config.jackson.BigIntegerSerializer;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BalanceDTO {
    @JsonSerialize(using = BigIntegerSerializer.class)
    @JsonDeserialize(using = BigIntegerDeserializer.class)
    private BigInteger amount;
    private String type;
    private String name;
    private String symbol;
}
