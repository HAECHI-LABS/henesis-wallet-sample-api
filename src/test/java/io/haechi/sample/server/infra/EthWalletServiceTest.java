package io.haechi.sample.server.infra;

import io.haechi.sample.server.domain.Transaction;
import io.haechi.sample.server.domain.Pagination;
import io.haechi.sample.server.domain.TransferEvent;
import io.haechi.sample.server.domain.value.Asset;
import io.haechi.sample.server.domain.value.Token;
import io.haechi.sample.server.domain.Wallet;
import io.haechi.sample.server.domain.value.HenesisTokenSymbol;
import io.haechi.sample.server.domain.value.TransactionHash;
import io.haechi.sample.server.domain.exceptions.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EthWalletServiceTest {
    private EthWalletService ethWalletService;

    @MockBean
    private EnclaveService enclaveService;

    @MockBean
    private HenesisWalletService henesisWalletService;

    @Before
    public void setup() {
        ethWalletService = new EthWalletService(
                enclaveService,
                henesisWalletService
        );
    }

    @Test
    public void createUserWalletTest() {
        // given
        String userId = "sample-userId";
        Wallet expectedWallet = Wallet.builder()
                .id("39d3c541e513c49180a6f93babf75434")
                .userId("sample-userId")
                .address("0x5fc41e1eef978a8ef69b455b67fb3920d0ad78df")
                .build();
        when(enclaveService.createUserWallet(userId)).thenReturn(expectedWallet);

        // when
        Wallet wallet = ethWalletService.createUserWallet(userId);

        // then
        assertThat(wallet.getId()).isEqualTo(expectedWallet.getId());
        assertThat(wallet.getUserId()).isEqualTo(expectedWallet.getUserId());
        assertThat(wallet.getAddress()).isEqualTo(expectedWallet.getAddress());
    }

    @Test
    public void findUserWalletByIdTest() {
        // given
        String walletId = "39d3c541e513c49180a6f93babf75434";
        Wallet expectedWallet = Wallet.builder()
                .id("39d3c541e513c49180a6f93babf75434")
                .userId("sample-userId")
                .address("0x5fc41e1eef978a8ef69b455b67fb3920d0ad78df")
                .build();
        when(enclaveService.getUserWallet(walletId)).thenReturn(expectedWallet);

        // when
        Wallet wallet = ethWalletService.findWalletByWalletId(walletId);

        // then
        assertThat(walletId).isEqualTo(expectedWallet.getId());
        assertThat(wallet.getId()).isEqualTo(expectedWallet.getId());
        assertThat(wallet.getUserId()).isEqualTo(expectedWallet.getUserId());
        assertThat(wallet.getAddress()).isEqualTo(expectedWallet.getAddress());
    }

    @Test
    public void findAssetByWalletIdAndTokenSymbolTest_WhenHasExpectedSymbol() {
        // given
        String walletId = "39d3c541e513c49180a6f93babf75434";
        Asset expectedAsset = Asset.builder()
                .name("envato")
                .token(
                        Token.builder()
                                .amount(BigInteger.valueOf(0))
                                .symbol("evt")
                                .build()
                )
                .build();

        List<Asset> resultAssets = Arrays.asList(
                Asset.builder()
                        .name("eth")
                        .token(
                                Token.builder()
                                        .amount(BigInteger.valueOf(0))
                                        .symbol("Eth")
                                        .build()
                        )
                        .build(),
                expectedAsset
        );
        when(enclaveService.getUserBalances(walletId)).thenReturn(resultAssets);

        // when
        Asset asset = ethWalletService.findAssetByWalletIdAndTokenSymbol(walletId, HenesisTokenSymbol.EVT.toString());

        // then
        assertThat(asset.getName()).isEqualTo(expectedAsset.getName());
        assertThat(asset.getToken().getAmount()).isEqualTo(expectedAsset.getToken().getAmount());
        assertThat(asset.getToken().getSymbol()).isEqualTo(expectedAsset.getToken().getSymbol());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void findAssetByWalletIdAndTokenSymbolTest_WhenHasNoExpectedSymbol() {
        // given
        String walletId = "39d3c541e513c49180a6f93babf75434";
        Asset expectedAsset = Asset.builder()
                .name("spin")
                .token(
                        Token.builder()
                                .amount(BigInteger.valueOf(0))
                                .symbol("Spin")
                                .build()
                )
                .build();

        List<Asset> resultAssets = Arrays.asList(
                Asset.builder()
                        .name("eth")
                        .token(
                                Token.builder()
                                        .amount(BigInteger.valueOf(0))
                                        .symbol("Eth")
                                        .build()
                        )
                        .build(),
                expectedAsset
        );
        when(enclaveService.getUserBalances(walletId)).thenReturn(resultAssets);

        // when and then
        ethWalletService.findAssetByWalletIdAndTokenSymbol(walletId, HenesisTokenSymbol.EVT.toString());
    }


    @Test
    public void findEventByWalletIdAndTransactionIdTest() {
        // given
        String walletId = "39d3c541e513c49180a6f93babf75434";
        Transaction transaction = Transaction.builder()
                .id("transactionId")
                .hash(new TransactionHash("0xc091d78ef59df2d7880ca0a9756393dcd6f90ebc286ffbab7455f1924b2c65d9"))
                .build();
        String transactionId = "transactionId";
        List<TransferEvent> expectedTransferEvents = Arrays.asList(
                TransferEvent.builder()
                        .transactionHash(transaction.getHash().getValue())
                        .from("from")
                        .to("to")
                        .build()
        );

        when(henesisWalletService.getTransaction(transactionId)).thenReturn(transaction);

        when(henesisWalletService.getTransferEvent(
                walletId, transaction.getHash().getValue())
        ).thenReturn(expectedTransferEvents);

        // when
        List<TransferEvent> transferEvents = ethWalletService.findEventByWalletIdAndTransactionId(walletId, transactionId);

        // then
        assertThat(transferEvents.size()).isEqualTo(1);
        assertThat(transferEvents.get(0).getTransactionHash()).isEqualTo(expectedTransferEvents.get(0).getTransactionHash());
        assertThat(transferEvents.get(0).getFrom()).isEqualTo(expectedTransferEvents.get(0).getFrom());
        assertThat(transferEvents.get(0).getTo()).isEqualTo(expectedTransferEvents.get(0).getTo());
    }

    @Test
    public void findAllEventsByWalletId() {
        // given
        Wallet wallet = Wallet.builder()
                .id("39d3c541e513c49180a6f93babf75434")
                .userId("sample-userId")
                .address("0x5fc41e1eef978a8ef69b455b67fb3920d0ad78df")
                .build();
        Pageable pageable = PageRequest.of(0, 10);
        Pagination<TransferEvent> expectedPagination = Pagination.<TransferEvent>builder()
                .previousUrl(null)
                .nextUrl(null)
                .totalCount(1L)
                .results(Arrays.asList(
                        TransferEvent.builder()
                                .transactionHash("transactionHash")
                                .from("from")
                                .to("to")
                                .build()
                ))
                .build();

        when(henesisWalletService.getTransferEvents(
                wallet, pageable)
        ).thenReturn(expectedPagination);

        // when
        Pagination<TransferEvent> pagination = ethWalletService.findAllEventsByWallet(wallet, pageable);

        // then
        assertThat(pagination.getResults().size()).isEqualTo(1);
        assertThat(pagination.getPagination().getPreviousUrl()).isEqualTo(expectedPagination.getPagination().getPreviousUrl());
        assertThat(pagination.getPagination().getNextUrl()).isEqualTo(expectedPagination.getPagination().getNextUrl());
        assertThat(pagination.getPagination().getTotalCount()).isEqualTo(expectedPagination.getPagination().getTotalCount());

    }
}
