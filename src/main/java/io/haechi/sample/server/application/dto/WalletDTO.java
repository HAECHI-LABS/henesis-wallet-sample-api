package io.haechi.sample.server.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalletDTO {
    @JsonProperty("walletId")
    private String id;
    private String userId;
    private String address;
}
