package io.haechi.sample.server.application.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferOwnerResultDTO extends ContractCallResponseDTO {
    public TransferOwnerResultDTO(
            String transactionId
    ) {
        super(transactionId);
    }
}
