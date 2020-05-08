package io.haechi.sample.server.infra.dto;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContractCallRequest {
    private String passphrase;
    private String contractAddress;
    private String value;
    private String data;
}
