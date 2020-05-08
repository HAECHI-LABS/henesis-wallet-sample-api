package io.haechi.sample.server.infra;

import io.haechi.sample.server.domain.value.Asset;
import io.haechi.sample.server.domain.value.Token;
import io.haechi.sample.server.domain.Wallet;
import io.haechi.sample.server.domain.value.TransactionReceipt;
import io.haechi.sample.server.infra.dto.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnclaveService extends HenesisWalletCallService {
    private final RestTemplate restTemplate;
    private final String walletPassphrase;

    public EnclaveService(
            @Qualifier("enclaveClient") RestTemplate enclaveRestTemplate,
            @Qualifier("walletPassphrase") String walletPassphrase
    ) {
        this.restTemplate = enclaveRestTemplate;
        this.walletPassphrase = walletPassphrase;
    }

    public TransactionReceipt contractCallOfMaster(String contractAddress, String payload, BigInteger value) {
        return this.serviceTemplate(() -> {
                    ContractCallResponseDTO responseDTO = restTemplate.postForEntity(
                            "/contract-call",
                            ContractCallRequest.builder()
                                    .passphrase(walletPassphrase)
                                    .contractAddress(contractAddress)
                                    .value("0x" + value.toString(16))
                                    .data(payload)
                                    .build(),
                            ContractCallResponseDTO.class
                    ).getBody();
                    return new TransactionReceipt(responseDTO.getTransactionId());
                }
        );
    }

    public TransactionReceipt contractCallOfUser(String walletId, String contractAddress, String payload, BigInteger value) {
        return this.serviceTemplate(() -> {
                    ContractCallResponseDTO responseDTO = restTemplate.postForEntity(
                            String.format("/user-wallets/%s/contract-call", walletId),
                            ContractCallRequest.builder()
                                    .passphrase(walletPassphrase)
                                    .contractAddress(contractAddress)
                                    .value("0x" + value.toString(16))
                                    .data(payload)
                                    .build(),
                            ContractCallResponseDTO.class
                    ).getBody();
                    return new TransactionReceipt(responseDTO.getTransactionId());
                }
        );
    }

    public Wallet createUserWallet(String userId) {
        return this.serviceTemplate(() -> {
            UserWalletDTO response = restTemplate.postForEntity(
                    "/user-wallets",
                    CreateUserWalletRequest.builder()
                            .name(userId)
                            .passphrase(walletPassphrase)
                            .build(),
                    UserWalletDTO.class
            ).getBody();
            return Wallet.builder()
                    .id(response.getId())
                    .userId(response.getName())
                    .address(response.getAddress())
                    .build();
        });
    }

    public Wallet getUserWallet(String walletId) {
        return this.serviceTemplate(() -> {
            UserWalletDTO response = restTemplate.getForEntity(
                    String.format("/user-wallets/%s", walletId),
                    UserWalletDTO.class
            ).getBody();
            return Wallet.builder()
                    .id(response.getId())
                    .userId(response.getName())
                    .address(response.getAddress())
                    .build();
        });
    }

    public List<Asset> getUserBalances(String walletId) {
        return this.serviceTemplate(() -> {
            List<BalanceDTO> balanceDTOs =
                    Arrays.asList(restTemplate.getForEntity(
                            String.format("/user-wallets/%s/balance",
                                    walletId),
                            BalanceDTO[].class
                            ).getBody()
                    );
            return balanceDTOs.stream()
                    .map((balanceDTO ->
                                    Asset.builder()
                                            .name(balanceDTO.getName())
                                            .token(Token.builder()
                                                    .symbol(balanceDTO.getSymbol())
                                                    .amount(balanceDTO.getAmount())
                                                    .build())
                                            .build()
                            )
                    ).collect(Collectors.toList());
        });
    }
}
