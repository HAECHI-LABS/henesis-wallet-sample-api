package io.haechi.sample.server.infra;

import io.haechi.sample.server.config.HenesisWalletConfig;
import io.haechi.sample.server.config.RestTemplateConfig;
import io.haechi.sample.server.domain.value.Asset;
import io.haechi.sample.server.domain.Wallet;
import io.haechi.sample.server.domain.value.TransactionReceipt;
import io.haechi.sample.server.infra.auth.HMACService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.List;

import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                RestTemplateConfig.class,
                HenesisWalletConfig.class,
                HMACService.class
        },
        initializers = {ConfigFileApplicationContextInitializer.class},
        loader = AnnotationConfigContextLoader.class
)
@ActiveProfiles("test")
public class EnclaveServiceTest {
    @Autowired
    @Qualifier("enclaveUrl")
    private String enclaveUrl;

    @Autowired
    @Qualifier("walletPassphrase")
    private String walletPassphrase;

    @Autowired
    @Qualifier("masterWalletId")
    private String masterWalletId;

    @Autowired
    @Qualifier("enclaveClient")
    private RestTemplate restTemplate;

    private EnclaveService enclaveService;

    private MockRestServiceServer mockServer;

    private String contractAddress;

    @Before
    public void setup() {
        contractAddress = "0x0000000000000000000000000000000000000000";
        mockServer = MockRestServiceServer.createServer(restTemplate);
        enclaveService = new EnclaveService(
                restTemplate,
                walletPassphrase
        );
    }

    @Test
    public void contractCallOfMasterTest() {
        // given
        String payload = "payload";
        BigInteger value = BigInteger.ZERO;
        String expectedResult = "{ \"id\" : \"db9c0da974de160a8bd51e66a4603f3d\"}";
        mockServer.expect(MockRestRequestMatchers.requestTo(
                String.format(
                        "%s/api/v1/wallets/%s/contract-call",
                        enclaveUrl,
                        masterWalletId
                )
        )).andRespond(withSuccess(expectedResult, MediaType.APPLICATION_JSON));

        // when
        TransactionReceipt receipt = enclaveService.contractCallOfMaster(
                contractAddress,
                payload,
                value
        );

        // then
        mockServer.verify();
        assertThat(receipt.getTransactionId()).isEqualTo("db9c0da974de160a8bd51e66a4603f3d");
    }

    @Test
    public void contractCallOfUserTest() {
        // given
        String userWalletId = "39d3c541e513c49180a6f93babf75434";
        String payload = "payload";
        BigInteger value = BigInteger.ZERO;
        String expectedResult = "{ " +
                "\"id\" : \"db9c0da974de160a8bd51e66a4603f3d\" \n" +
                "}";
        mockServer.expect(MockRestRequestMatchers.requestTo(
                String.format(
                        "%s/api/v1/wallets/%s/user-wallets/%s/contract-call",
                        enclaveUrl,
                        masterWalletId,
                        userWalletId

                )
        )).andRespond(withSuccess(expectedResult, MediaType.APPLICATION_JSON));

        // when
        TransactionReceipt receipt = enclaveService.contractCallOfUser(
                userWalletId,
                contractAddress,
                payload,
                value
        );

        // then
        mockServer.verify();
        assertThat(receipt.getTransactionId()).isEqualTo("db9c0da974de160a8bd51e66a4603f3d");
    }

    @Test
    public void createUserWallet() {
        // given
        String userId = "sample-internal-user-id";
        String expectedResult = "{\n" +
                "\"id\": \"39d3c541e513c49180a6f93babf75434\",\n" +
                "  \"name\": \"sample-internal-user-id\",\n" +
                "  \"address\": \"0x5fc41e1eef978a8ef69b455b67fb3920d0ad78df\",\n" +
                "  \"blockchain\": \"ETHEREUM\",\n" +
                " \"status\": \"ACTIVE\",\n" +
                "  \"createdAt\": \"1586669984828\",\n" +
                " \"updatedAt\": \"1586670195971\" \n" +
                "}";
        mockServer.expect(MockRestRequestMatchers.requestTo(
                String.format(
                        "%s/api/v1/wallets/%s/user-wallets",
                        enclaveUrl,
                        masterWalletId

                )
        )).andRespond(withSuccess(expectedResult, MediaType.APPLICATION_JSON));

        // when
        Wallet wallet = enclaveService.createUserWallet(userId);

        // then
        mockServer.verify();
        assertThat(wallet.getId()).isEqualTo("39d3c541e513c49180a6f93babf75434");
        assertThat(wallet.getUserId()).isEqualTo("sample-internal-user-id");
        assertThat(wallet.getAddress()).isEqualTo("0x5fc41e1eef978a8ef69b455b67fb3920d0ad78df");
    }

    @Test
    public void getUserWallet() {
        // given
        String userWalletId = "39d3c541e513c49180a6f93babf75434";
        String expectedResult = "{\n" +
                "\"id\": \"39d3c541e513c49180a6f93babf75434\",\n" +
                "  \"name\": \"sample-internal-user-id\",\n" +
                "  \"address\": \"0x5fc41e1eef978a8ef69b455b67fb3920d0ad78df\",\n" +
                "  \"blockchain\": \"ETHEREUM\",\n" +
                " \"status\": \"ACTIVE\",\n" +
                "  \"createdAt\": \"1586669984828\",\n" +
                " \"updatedAt\": \"1586670195971\" \n" +
                "}";
        mockServer.expect(MockRestRequestMatchers.requestTo(
                String.format(
                        "%s/api/v1/wallets/%s/user-wallets/%s",
                        enclaveUrl,
                        masterWalletId,
                        userWalletId

                )
        )).andRespond(withSuccess(expectedResult, MediaType.APPLICATION_JSON));

        // when
        Wallet wallet = enclaveService.getUserWallet(userWalletId);

        // then
        mockServer.verify();
        assertThat(wallet.getId()).isEqualTo("39d3c541e513c49180a6f93babf75434");
        assertThat(wallet.getUserId()).isEqualTo("sample-internal-user-id");
        assertThat(wallet.getAddress()).isEqualTo("0x5fc41e1eef978a8ef69b455b67fb3920d0ad78df");
    }

    @Test
    public void getUserBalances() {
        // given
        String userWalletId = "39d3c541e513c49180a6f93babf75434";
        String expectedResult = "\n" +
                "[\n" +
                "  {\n" +
                "        \"coinType\": \"ETHEREUM\",\n" +
                "        \"amount\": \"0x0\",\n" +
                "        \"name\": \"eth\",\n" +
                "        \"symbol\": \"Eth\"\n" +
                "    },\n" +
                "\t\t{\n" +
                "        \"coinType\": \"ERC20\",\n" +
                "        \"amount\": \"0x2710\",\n" +
                "        \"name\": \"envato\",\n" +
                "        \"symbol\": \"EVT\"\n" +
                "    }\n" +
                "]";
        mockServer.expect(MockRestRequestMatchers.requestTo(
                String.format(
                        "%s/api/v1/wallets/%s/user-wallets/%s/balance",
                        enclaveUrl,
                        masterWalletId,
                        userWalletId
                )
        )).andRespond(withSuccess(expectedResult, MediaType.APPLICATION_JSON));

        // when
        List<Asset> assets = enclaveService.getUserBalances(userWalletId);

        // then
        mockServer.verify();
        assertThat(assets.size()).isEqualTo(2);
        assertThat(assets.get(0).getToken().getSymbol()).isEqualTo("Eth");
        assertThat(assets.get(1).getToken().getSymbol()).isEqualTo("Evt");
    }
}
