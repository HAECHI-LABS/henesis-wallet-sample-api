package io.haechi.sample.server.infra.dto;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateUserWalletRequest {
    private String name;
    private String passphrase;
}
