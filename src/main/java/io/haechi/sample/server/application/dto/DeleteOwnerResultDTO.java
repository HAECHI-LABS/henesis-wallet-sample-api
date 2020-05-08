package io.haechi.sample.server.application.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeleteOwnerResultDTO extends ContractCallResponseDTO {
    public DeleteOwnerResultDTO(
            String transactionId
    ) {
        super(transactionId);
    }
}
