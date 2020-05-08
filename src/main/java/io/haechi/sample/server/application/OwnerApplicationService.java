package io.haechi.sample.server.application;

import io.haechi.sample.server.application.dto.DeleteOwnerResultDTO;
import io.haechi.sample.server.application.dto.OwnerDTO;
import io.haechi.sample.server.application.dto.TransferOwnerRequestDTO;
import io.haechi.sample.server.application.dto.TransferOwnerResultDTO;
import io.haechi.sample.server.domain.OwnerService;
import io.haechi.sample.server.domain.value.Address;
import org.springframework.stereotype.Service;

@Service
public class OwnerApplicationService {
    private OwnerService ownerService;
    public OwnerApplicationService(
            OwnerService ownerService
    ) {
        this.ownerService = ownerService;
    }

    public TransferOwnerResultDTO transfer(TransferOwnerRequestDTO request) {
        return new TransferOwnerResultDTO(
                this.ownerService.transfer(new Address(request.getNewOwner())).getTransactionId()
        );
    }

    public DeleteOwnerResultDTO renounce() {
        return new DeleteOwnerResultDTO(
                this.ownerService.delete().getTransactionId()
        );
    }

    public OwnerDTO find() {
        return OwnerDTO.builder().address(
                this.ownerService.find().getAddress()
        ).build();
    }
}
