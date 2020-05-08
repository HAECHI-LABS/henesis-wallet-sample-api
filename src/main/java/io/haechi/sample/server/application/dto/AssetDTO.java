package io.haechi.sample.server.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssetDTO {
    private String name;
    private String symbol;
    private BigInteger amount;
}
