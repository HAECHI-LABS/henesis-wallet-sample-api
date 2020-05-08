package io.haechi.sample.server.infra;

import io.haechi.sample.server.domain.Owner;
import io.haechi.sample.server.domain.OwnerService;
import io.haechi.sample.server.domain.value.Address;
import io.haechi.sample.server.domain.value.TransactionReceipt;
import io.haechi.sample.server.infra.contracts.Ownable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class EthOwnerService extends ContractService implements OwnerService {
    private Ownable ownable;

    public EthOwnerService(
            EnclaveService enclaveService,
            @Qualifier("ownableAddress") Address ownableAddress,
            Ownable ownable
    ) {
        super(enclaveService, ownableAddress);
        this.ownable = ownable;
    }

    @Override
    public TransactionReceipt transfer(Address newOwner) {
        String payload = this.ownable.transferOwnership(newOwner.toString()).encodeFunctionCall();
        return sendTransactionAsMaster(payload);
    }
    @Override
    public TransactionReceipt delete() {
        String payload = this.ownable.renounceOwnership().encodeFunctionCall();
        return sendTransactionAsMaster(payload);
    }

    @Override
    public Owner find(){
        return viewFunctionCallTemplate(()->Owner.builder().address(this.ownable.owner().send()).build());
    }

}
