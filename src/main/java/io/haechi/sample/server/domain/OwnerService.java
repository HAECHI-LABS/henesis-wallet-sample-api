package io.haechi.sample.server.domain;

import io.haechi.sample.server.domain.value.Address;
import io.haechi.sample.server.domain.value.TransactionReceipt;

public interface OwnerService {
    TransactionReceipt transfer(Address newOwner);
    TransactionReceipt delete();
    Owner find();
}
