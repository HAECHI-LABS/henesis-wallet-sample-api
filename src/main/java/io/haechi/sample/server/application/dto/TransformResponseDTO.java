package io.haechi.sample.server.application.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransformResponseDTO extends ContractCallResponseDTO {
    public TransformResponseDTO(String transactionId) {
        super(transactionId);
    }
}
