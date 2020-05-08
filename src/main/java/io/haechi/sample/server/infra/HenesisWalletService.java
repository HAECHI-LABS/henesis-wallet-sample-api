package io.haechi.sample.server.infra;

import io.haechi.sample.server.domain.Pagination;
import io.haechi.sample.server.domain.Transaction;
import io.haechi.sample.server.domain.Wallet;
import io.haechi.sample.server.domain.value.Address;
import io.haechi.sample.server.domain.value.TransactionHash;
import io.haechi.sample.server.domain.TransferEvent;
import io.haechi.sample.server.domain.value.TransactionStatus;
import io.haechi.sample.server.infra.dto.TransactionDTO;
import io.haechi.sample.server.infra.dto.TransferEventResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class HenesisWalletService extends HenesisWalletCallService {
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;

    public HenesisWalletService(
            @Qualifier("walletClient") RestTemplate restTemplate,
            ModelMapper modelMapper
    ) {
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
    }

    public Transaction getTransaction(String transactionId) {
        return this.serviceTemplate(() -> {
                    TransactionDTO transactionDTO = restTemplate.getForEntity(
                            String.format("/transactions/%s?blockchain=ETHEREUM", transactionId),
                            TransactionDTO.class
                    ).getBody();

                    return Transaction.builder()
                            .id(transactionDTO.getId())
                            .from(new Address(transactionDTO.getSignedMultiSigPayloadDTO().getPayloadDTO().getWalletAddress()))
                            .to(new Address(transactionDTO.getSignedMultiSigPayloadDTO().getPayloadDTO().getToAddress()))
                            .hash(new TransactionHash(transactionDTO.getHash()))
                            .status(TransactionStatus.valueOf(transactionDTO.getStatus()))
                            .error(transactionDTO.getError())
                            .build();
                }
        );
    }

    public List<TransferEvent> getTransferEvent(String walletId, String transactionHash) {
        return this.serviceTemplate(() -> {
                    Pagination<TransferEventResponseDTO> pagination = restTemplate.exchange(
                            String.format(
                                    "/value-transfer-events?wallet_id=%s&transaction_hash=%s",
                                    walletId,
                                    transactionHash
                            ),
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<Pagination<TransferEventResponseDTO>>() {
                            }
                    ).getBody();
                    return Arrays.asList(modelMapper.map(pagination.getResults(), TransferEvent[].class));
                }
        );
    }

    public Pagination<TransferEvent> getTransferEvents(Wallet wallet, Pageable pageable) {
        return this.serviceTemplate(() -> {
                    Pagination<TransferEventResponseDTO> pagination = restTemplate.exchange(
                            String.format(
                                    "/value-transfer-events?wallet_id=%s&address=%s&pageNumber=%s&pageSize=%s",
                                    wallet.getId(),
                                    wallet.getAddress(),
                                    pageable.getPageNumber(),
                                    pageable.getPageSize()
                            ),
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<Pagination<TransferEventResponseDTO>>() {
                            }
                    ).getBody();

                    return Pagination.<TransferEvent>builder()
                            .previousUrl(pagination.getPagination().getPreviousUrl())
                            .nextUrl(pagination.getPagination().getNextUrl())
                            .totalCount(pagination.getPagination().getTotalCount())
                            .results(Arrays.asList(modelMapper.map(pagination.getResults(), TransferEvent[].class)))
                            .build();
                }
        );
    }
}
