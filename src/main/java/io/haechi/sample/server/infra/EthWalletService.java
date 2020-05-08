package io.haechi.sample.server.infra;

import static com.google.common.collect.MoreCollectors.onlyElement;

import io.haechi.sample.server.domain.value.Asset;
import io.haechi.sample.server.domain.TransferEvent;
import io.haechi.sample.server.domain.exceptions.ResourceNotFoundException;
import io.haechi.sample.server.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class EthWalletService implements WalletService {
    private final EnclaveService enclaveService;
    private final HenesisWalletService henesisWalletService;

    public EthWalletService(
            EnclaveService enclaveService,
            HenesisWalletService henesisWalletService
    ) {
        this.enclaveService = enclaveService;
        this.henesisWalletService = henesisWalletService;
    }

    @Override
    public Wallet createUserWallet(String userId) {
        return enclaveService.createUserWallet(userId);
    }

    @Override
    public Wallet findWalletByWalletId(String walletId) {
        return enclaveService.getUserWallet(walletId);
    }

    @Override
    public Asset findAssetByWalletIdAndTokenSymbol(String walletId, String symbol) {
        List<Asset> assets = enclaveService.getUserBalances(walletId);
        try {
            return assets.stream()
                    .filter((asset) -> asset.getToken().getSymbol().toLowerCase().equals(symbol.toLowerCase()))
                    .collect(onlyElement());
        } catch (Exception e) {
            throw new ResourceNotFoundException(String.format("Has no token matched symbol %s", symbol), e);
        }
    }

    @Override
    public Transaction findTransactionByTransactionId(String transactionId) {
        return henesisWalletService.getTransaction(transactionId);
    }

    @Override
    public List<TransferEvent> findEventByWalletIdAndTransactionId(String walletId, String transactionId) {
        Transaction transaction = henesisWalletService.getTransaction(transactionId);
        return henesisWalletService.getTransferEvent(walletId, transaction.getHash().getValue());
    }

    @Override
    public Pagination<TransferEvent> findAllEventsByWallet(Wallet wallet, Pageable pageable) {
        return henesisWalletService.getTransferEvents(wallet, pageable);
    }
}
