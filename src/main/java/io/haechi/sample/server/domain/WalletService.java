package io.haechi.sample.server.domain;

import io.haechi.sample.server.domain.value.Asset;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WalletService {
    Wallet createUserWallet(String userId);
    Wallet findWalletByWalletId(String walletId);
    Asset findAssetByWalletIdAndTokenSymbol(String walletId, String symbol);
    Transaction findTransactionByTransactionId(String transactionId);
    List<TransferEvent> findEventByWalletIdAndTransactionId(String walletId, String transactionId);
    Pagination<TransferEvent> findAllEventsByWallet(Wallet wallet, Pageable pageable);
}
