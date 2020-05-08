package io.haechi.sample.server.application.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalletCreateRequestDTO {
    @NotNull(message = "userId must be not null")
    @NotEmpty(message = "userId must be not empty")
    private String userId;
}
