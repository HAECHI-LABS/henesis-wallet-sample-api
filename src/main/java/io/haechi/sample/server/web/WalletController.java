package io.haechi.sample.server.web;

import io.haechi.sample.server.application.WalletApplicationService;
import io.haechi.sample.server.application.dto.*;
import io.haechi.sample.server.domain.Pagination;
import io.haechi.sample.server.application.dto.TransferEventDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("wallet")
public class  WalletController {
    private final WalletApplicationService walletApplicationService;

    public WalletController(WalletApplicationService walletApplicationService) {
        this.walletApplicationService = walletApplicationService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public WalletDTO createUserWallet(@RequestBody @Valid WalletCreateRequestDTO walletCreateRequestDTO) {
        return walletApplicationService.createUserWallet(walletCreateRequestDTO.getUserId());
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public WalletDTO findUserWallet(@RequestParam String walletId) {
        return walletApplicationService.findWalletByWalletId(walletId);
    }

    @GetMapping("/asset/{tokenSymbol}")
    @ResponseStatus(value = HttpStatus.OK)
    public AssetDTO findAsset(
            @PathVariable String tokenSymbol,
            @RequestParam String walletId
    ) {
        return walletApplicationService.findAssetByWalletIdAndTokenSymbol(walletId, tokenSymbol);
    }

    @GetMapping("/transactions/{transactionId}")
    @ResponseStatus(value = HttpStatus.OK)
    public TransactionResponseDTO findTransaction(
            @PathVariable String transactionId
    ) {
        return walletApplicationService.findTransactionByTransactionId(transactionId);
    }

    @GetMapping("/transactions/events")
    @ResponseStatus(value = HttpStatus.OK)
    public Pagination<TransferEventDTO> findEvent(
            @RequestParam String walletId,
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) @PageableDefault(size = 15) final Pageable pageable
    ) {
        return walletApplicationService.findAllEventsByWalletId(walletId, pageable);
    }

    @GetMapping("/transactions/{transactionId}/events")
    @ResponseStatus(value = HttpStatus.OK)
    public List<TransferEventDTO> findEvent(
            @RequestParam String walletId,
            @PathVariable String transactionId
    ) {
        return walletApplicationService.findEventByWalletIdAndTransactionId(walletId, transactionId);
    }
}
