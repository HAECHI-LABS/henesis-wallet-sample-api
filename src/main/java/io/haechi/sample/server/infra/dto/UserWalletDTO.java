package io.haechi.sample.server.infra.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserWalletDTO {
    private String id;
    private String name;
    private String address;
    private String blockchain;
    private String status;
    private String createdAt;
}
