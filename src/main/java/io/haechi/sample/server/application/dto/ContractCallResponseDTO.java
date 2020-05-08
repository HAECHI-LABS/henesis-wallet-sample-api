package io.haechi.sample.server.application.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContractCallResponseDTO {
    private String transactionId;
}
