package io.haechi.sample.server.web;

import io.haechi.sample.server.application.WalletApplicationService;
import io.haechi.sample.server.application.dto.*;
import io.haechi.sample.server.domain.value.HenesisTokenSymbol;
import io.haechi.sample.server.helper.HttpRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private WalletApplicationService walletApplicationService;

    @Test
    public void createUserWalletTest() {
        // given
        String userId = "sample-userId";
        WalletDTO expectedWallet = new WalletDTO(
                "39d3c541e513c49180a6f93babf75434",
                userId,
                "0x5fc41e1eef978a8ef69b455b67fb3920d0ad78df"
        );
        when(walletApplicationService.createUserWallet(userId)).thenReturn(expectedWallet);

        // when
        ResponseEntity<WalletDTO> response = testRestTemplate.exchange(
                "/wallet",
                HttpMethod.POST,
                HttpRequest.getAuthorizedHttpEntity(
                        new WalletCreateRequestDTO(
                                userId
                        )
                ),
                WalletDTO.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(expectedWallet.getId());
        assertThat(response.getBody().getUserId()).isEqualTo(expectedWallet.getUserId());
        assertThat(response.getBody().getAddress()).isEqualTo(expectedWallet.getAddress());
    }

    @Test
    public void findUserWalletTest() {
        // given
        String walletId = "sample-userId";
        WalletDTO expectedWallet = new WalletDTO(
                "39d3c541e513c49180a6f93babf75434",
                "sample-userId",
                "0x5fc41e1eef978a8ef69b455b67fb3920d0ad78df"
        );
        when(walletApplicationService.findWalletByWalletId(walletId)).thenReturn(expectedWallet);

        // when
        ResponseEntity<WalletDTO> response = testRestTemplate.exchange(
                String.format("/wallet?walletId=%s", walletId),
                HttpMethod.GET,
                HttpRequest.getAuthorizedHttpEntity(),
                WalletDTO.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(expectedWallet.getId());
        assertThat(response.getBody().getUserId()).isEqualTo(expectedWallet.getUserId());
        assertThat(response.getBody().getAddress()).isEqualTo(expectedWallet.getAddress());
    }

    @Test
    public void findAssetTest() {
        // given
        String walletId = "sample-userId";
        AssetDTO expectedAsset = new AssetDTO(
                "envato",
                "EVT",
                BigInteger.valueOf(100)
        );
        when(walletApplicationService.findAssetByWalletIdAndTokenSymbol(walletId, HenesisTokenSymbol.EVT.toString())).thenReturn(expectedAsset);

        // when
        ResponseEntity<AssetDTO> response = testRestTemplate.exchange(
                String.format("/wallet/asset?walletId=%s", walletId),
                HttpMethod.GET,
                HttpRequest.getAuthorizedHttpEntity(),
                AssetDTO.class
        );

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo(expectedAsset.getName());
        assertThat(response.getBody().getAmount()).isEqualTo(expectedAsset.getAmount());
        assertThat(response.getBody().getSymbol()).isEqualTo(expectedAsset.getSymbol());
    }
}
