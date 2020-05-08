package io.haechi.sample.server.application.dto;

import io.haechi.sample.server.domain.value.HenesisTokenSymbol;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssetTransformRequestDTO {
    private String walletId;
    private BigInteger amount;
    private HenesisTokenSymbol tokenSymbol;
}
