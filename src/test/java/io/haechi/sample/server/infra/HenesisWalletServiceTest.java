package io.haechi.sample.server.infra;

import io.haechi.sample.server.domain.Pagination;
import io.haechi.sample.server.domain.Transaction;
import io.haechi.sample.server.domain.TransferEvent;
import io.haechi.sample.server.domain.Wallet;
import io.haechi.sample.server.domain.value.TransactionStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class HenesisWalletServiceTest {
    @Autowired
    @Qualifier("walletUrl")
    private String walletUrl;

    @Autowired
    private HenesisWalletService henesisWalletService;

    @Autowired
    @Qualifier("walletClient")
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Before
    public void setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void getTransactionTest() {
        // given
        String transactionId = "transactionId";
        String expectedResult = "{\n" +
                "    \"id\": \"055fcf9d6d02985f6570507a2a5741ec\",\n" +
                "    \"blockchain\": \"ETHEREUM\",\n" +
                "    \"sender\": \"0x4ef3ba60c8710f45371835cddafabf33daa83e1d\",\n" +
                "    \"hash\": \"0x4e12f7fa4f497f2e6fb1ae062b3b0f8756a891f4baa280fdf871ad3e1836cf36\",\n" +
                "    \"error\": null,\n" +
                "    \"status\": \"CONFIRMED\",\n" +
                "    \"key_id\": \"52e779750bb1330d2f23439c6da821ee\",\n" +
                "    \"signed_multi_sig_payload\": {\n" +
                "        \"signature\": \"0x934ce438e5661b7e59b9d7e22d247626d9809a3bf78398a43b46b9fb5e8001b50d98a61a6cb6ee329aa53a062c84035abdf1b3bf2db3b12153287263264bbf061b\",\n" +
                "        \"payload\": {\n" +
                "            \"value\": \"0x0\",\n" +
                "            \"wallet_address\": \"0xeca3bf7b4344114f5fe6084e7c08d49bcca7c907\",\n" +
                "            \"to_address\": \"0xeca3bf7b4344114f5fe6084e7c08d49bcca7c907\",\n" +
                "            \"wallet_nonce\": \"0x26aec51aa2ce4b1579a12ae1c371bea6e8db488776b861255b80c4e621a1142c\",\n" +
                "            \"hex_data\": \"0x5a7747fca57c57c60f6389fdf94036a3bcf04209098865647d4875cfd9b66ba17bbae41d\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"raw_transaction\": {\n" +
                "        \"nonce\": \"0x2a\",\n" +
                "        \"to\": \"0xeca3bf7b4344114f5fe6084e7c08d49bcca7c907\",\n" +
                "        \"value\": \"0x0\",\n" +
                "        \"data\": \"0x4867ba1500000000000000000000000000000000000000000000000000000000000000c0000000000000000000000000eca3bf7b4344114f5fe6084e7c08d49bcca7c907000000000000000000000000eca3bf7b4344114f5fe6084e7c08d49bcca7c907000000000000000000000000000000000000000000000000000000000000000026aec51aa2ce4b1579a12ae1c371bea6e8db488776b861255b80c4e621a1142c00000000000000000000000000000000000000000000000000000000000001400000000000000000000000000000000000000000000000000000000000000041934ce438e5661b7e59b9d7e22d247626d9809a3bf78398a43b46b9fb5e8001b50d98a61a6cb6ee329aa53a062c84035abdf1b3bf2db3b12153287263264bbf061b0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000245a7747fca57c57c60f6389fdf94036a3bcf04209098865647d4875cfd9b66ba17bbae41d00000000000000000000000000000000000000000000000000000000\",\n" +
                "        \"gas_price\": \"0x2540be400\",\n" +
                "        \"gas_limit\": \"0x419ce0\"\n" +
                "    }\n" +
                "}";
        mockServer.expect(MockRestRequestMatchers.requestTo(
                String.format(
                        "%s/api/v1/transactions/%s?blockchain=ETHEREUM",
                        walletUrl,
                        transactionId
                )
        )).andRespond(withSuccess(expectedResult, MediaType.APPLICATION_JSON));

        // when
        Transaction transaction = henesisWalletService.getTransaction(transactionId);

        // then
        mockServer.verify();
        assertThat(transaction.getHash().getValue()).isEqualTo("0x4e12f7fa4f497f2e6fb1ae062b3b0f8756a891f4baa280fdf871ad3e1836cf36");
    }

    @Test
    public void getTransferEvent() {
        // given
        String walletId = "a1980cd99bfbd582efb04c7a0325d084";
        String transactionHash = "0x36113fbc1360f631fb6fc16140a1d584dd9c3cb2b768d87173d304534af8c8b0";
        String expectedResult = "{\n" +
                "    \"pagination\": {\n" +
                "        \"next_url\": null,\n" +
                "        \"previous_url\": null,\n" +
                "        \"total_count\": 1\n" +
                "    },\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"from\": \"0x453011a4f948b22762290c1f4de3e2210c311c06\",\n" +
                "            \"to\": \"0xeca3bf7b4344114f5fe6084e7c08d49bcca7c907\",\n" +
                "            \"amount\": \"0x3635c9adc5dea00000\",\n" +
                "            \"status\": \"CONFIRMED\",\n" +
                "            \"wallet_id\": \"a1980cd99bfbd582efb04c7a0325d084\",\n" +
                "            \"coin_symbol\": \"EVT\",\n" +
                "            \"transfer_type\": \"DEPOSIT\",\n" +
                "            \"transaction_hash\": \"0x36113fbc1360f631fb6fc16140a1d584dd9c3cb2b768d87173d304534af8c8b0\",\n" +
                "            \"created_at\": \"1587019987483\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        mockServer.expect(MockRestRequestMatchers.requestTo(
                String.format(
                        "%s/api/v1/value-transfer-events?wallet_id=%s&transaction_hash=%s",
                        walletUrl,
                        walletId,
                        transactionHash
                )
        )).andRespond(withSuccess(expectedResult, MediaType.APPLICATION_JSON));

        // when
        List<TransferEvent> transferEvents = henesisWalletService.getTransferEvent(walletId, transactionHash);

        // then
        mockServer.verify();
        assertThat(transferEvents.size()).isEqualTo(1);
        assertThat(transferEvents.get(0).getFrom()).isEqualTo("0x453011a4f948b22762290c1f4de3e2210c311c06");
        assertThat(transferEvents.get(0).getTo()).isEqualTo("0xeca3bf7b4344114f5fe6084e7c08d49bcca7c907");
        assertThat("0x" + transferEvents.get(0).getAmount().toString(16)).isEqualTo("0x3635c9adc5dea00000");
        assertThat(transferEvents.get(0).getStatus()).isEqualTo(TransactionStatus.CONFIRMED);
        assertThat(transferEvents.get(0).getWalletId()).isEqualTo(walletId);
        assertThat(transferEvents.get(0).getCoinSymbol()).isEqualTo("EVT");
        assertThat(transferEvents.get(0).getTransferType()).isEqualTo("DEPOSIT");
        assertThat(transferEvents.get(0).getTransactionHash()).isEqualTo(transactionHash);
        assertThat(transferEvents.get(0).getCreatedAt()).isEqualTo("1587019987483");
    }

    @Test
    public void getTransferEvents() {
        // given
        Wallet wallet = Wallet.builder()
                .id("39d3c541e513c49180a6f93babf75434")
                .userId("sample-userId")
                .address("0x5fc41e1eef978a8ef69b455b67fb3920d0ad78df")
                .build();
        Pageable pageable = PageRequest.of(0, 10);
        String expectedResult = "{\n" +
                "    \"pagination\": {\n" +
                "        \"next_url\": null,\n" +
                "        \"previous_url\": null,\n" +
                "        \"total_count\": 2\n" +
                "    },\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"from\": \"0x453011a4f948b22762290c1f4de3e2210c311c06\",\n" +
                "            \"to\": \"0xeca3bf7b4344114f5fe6084e7c08d49bcca7c907\",\n" +
                "            \"amount\": \"0x3635c9adc5dea00000\",\n" +
                "            \"status\": \"CONFIRMED\",\n" +
                "            \"wallet_id\": \"a1980cd99bfbd582efb04c7a0325d084\",\n" +
                "            \"coin_symbol\": \"EVT\",\n" +
                "            \"transfer_type\": \"DEPOSIT\",\n" +
                "            \"transaction_hash\": \"0x36113fbc1360f631fb6fc16140a1d584dd9c3cb2b768d87173d304534af8c8b0\",\n" +
                "            \"created_at\": \"1587019987483\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"from\": \"0x453011a4f948b22762290c1f4de3e2210c311c06\",\n" +
                "            \"to\": \"0xeca3bf7b4344114f5fe6084e7c08d49bcca7c907\",\n" +
                "            \"amount\": \"0x8ac7230489e80000\",\n" +
                "            \"status\": \"CONFIRMED\",\n" +
                "            \"wallet_id\": \"a1980cd99bfbd582efb04c7a0325d084\",\n" +
                "            \"coin_symbol\": \"EVT\",\n" +
                "            \"transfer_type\": \"DEPOSIT\",\n" +
                "            \"transaction_hash\": \"0x3cded02f6095ef8870f763a5b026fb8ce2a03205fe0ae22cff01cfbdd4ee7216\",\n" +
                "            \"created_at\": \"1587019588719\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        mockServer.expect(MockRestRequestMatchers.requestTo(
                String.format(
                        "%s/api/v1/value-transfer-events?wallet_id=%s&address=%s&pageNumber=%s&pageSize=%s",
                        walletUrl,
                        wallet.getId(),
                        wallet.getAddress(),
                        pageable.getPageNumber(),
                        pageable.getPageSize()
                )
        )).andRespond(withSuccess(expectedResult, MediaType.APPLICATION_JSON));

        // when
        Pagination<TransferEvent> transferEvents = henesisWalletService.getTransferEvents(wallet, pageable);

        // then
        mockServer.verify();
        assertThat(transferEvents.getPagination().getPreviousUrl()).isNull();
        assertThat(transferEvents.getPagination().getNextUrl()).isNull();
        assertThat(transferEvents.getPagination().getTotalCount()).isEqualTo(2);
        assertThat(transferEvents.getResults().size()).isEqualTo(2);
    }
}
