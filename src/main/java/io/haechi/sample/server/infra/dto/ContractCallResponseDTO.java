package io.haechi.sample.server.infra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContractCallResponseDTO {
    @JsonProperty("id")
    private String transactionId;
}
