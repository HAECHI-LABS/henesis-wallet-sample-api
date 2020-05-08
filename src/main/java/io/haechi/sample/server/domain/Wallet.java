package io.haechi.sample.server.domain;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wallet {
    private String id;
    private String userId;
    private String address;
}
