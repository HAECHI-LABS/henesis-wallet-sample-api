package io.haechi.sample.server.infra;

import io.haechi.sample.server.domain.value.Address;
import io.haechi.sample.server.domain.value.TransactionReceipt;
import io.haechi.sample.server.infra.exceptions.FunctionCallException;

import java.math.BigInteger;

abstract class ContractService {
    private final EnclaveService enclaveService;
    private final Address contractAddress;

    public ContractService(
            EnclaveService enclaveService,
            Address contractAddress
    ) {
        this.enclaveService = enclaveService;
        this.contractAddress = contractAddress;
    }

    protected <T> T viewFunctionCallTemplate(ViewFunctionCallback<T> callback) {
        try {
            return callback.send();
        } catch (Exception e) {
            throw new FunctionCallException(e);
        }
    }

    interface ViewFunctionCallback<T> {
        T send() throws Exception;
    }

    public TransactionReceipt sendTransactionAsMaster(String payload) {
        return enclaveService.contractCallOfMaster(contractAddress.getValue(), payload, BigInteger.ZERO);
    }

    public TransactionReceipt sendTransactionAsUser(String walletId, String payload) {
        return enclaveService.contractCallOfUser(walletId, contractAddress.getValue(), payload, BigInteger.ZERO);
    }
}
