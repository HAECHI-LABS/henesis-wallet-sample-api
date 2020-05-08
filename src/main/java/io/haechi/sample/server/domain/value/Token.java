package io.haechi.sample.server.domain.value;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {
    private String symbol;
    private BigInteger amount;
}
