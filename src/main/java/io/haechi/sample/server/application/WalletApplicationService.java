package io.haechi.sample.server.application;

import io.haechi.sample.server.application.dto.TransferEventDTO;
import io.haechi.sample.server.application.dto.AssetDTO;
import io.haechi.sample.server.application.dto.TransactionResponseDTO;
import io.haechi.sample.server.application.dto.WalletDTO;
import io.haechi.sample.server.domain.Pagination;
import io.haechi.sample.server.domain.Transaction;
import io.haechi.sample.server.domain.TransferEvent;
import io.haechi.sample.server.domain.Wallet;
import io.haechi.sample.server.domain.WalletService;
import io.haechi.sample.server.domain.value.Asset;
import io.haechi.sample.server.domain.value.HenesisTokenSymbol;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class WalletApplicationService {
    private final WalletService walletService;
    private final ModelMapper modelMapper;

    public WalletApplicationService(
            WalletService walletService,
            ModelMapper modelMapper
    ) {
        this.walletService = walletService;
        this.modelMapper = modelMapper;
    }

    public WalletDTO createUserWallet(String userId) {
        Wallet wallet = walletService.createUserWallet(userId);
        return modelMapper.map(wallet, WalletDTO.class);
    }

    public WalletDTO findWalletByWalletId(String walletId) {
        Wallet wallet = walletService.findWalletByWalletId(walletId);
        return modelMapper.map(wallet, WalletDTO.class);
    }

    public AssetDTO findAssetByWalletIdAndTokenSymbol(String walletId, String symbol) {
        Asset asset = walletService.findAssetByWalletIdAndTokenSymbol(walletId, symbol);
        return modelMapper.map(asset, AssetDTO.class);
    }

    public TransactionResponseDTO findTransactionByTransactionId(String transactionId) {
        Transaction transaction = walletService.findTransactionByTransactionId(transactionId);
        return modelMapper.map(transaction, TransactionResponseDTO.class);
    }

    public List<TransferEventDTO> findEventByWalletIdAndTransactionId(String walletId, String transactionId) {
        List<TransferEvent> events = walletService.findEventByWalletIdAndTransactionId(walletId, transactionId);
        return Arrays.asList(modelMapper.map(events, TransferEventDTO[].class));
    }

    public Pagination<TransferEventDTO> findAllEventsByWalletId(String walletId, Pageable pageable) {
        Wallet wallet = walletService.findWalletByWalletId(walletId);
        Pagination<TransferEvent> pagination = walletService.findAllEventsByWallet(wallet, pageable);
        return Pagination.<TransferEventDTO>builder()
                .previousUrl(pagination.getPagination().getPreviousUrl())
                .nextUrl(pagination.getPagination().getNextUrl())
                .totalCount(pagination.getPagination().getTotalCount())
                .results(Arrays.asList(modelMapper.map(pagination.getResults(), TransferEventDTO[].class)))
                .build();
    }
}
