package io.haechi.sample.server.application;

import io.haechi.sample.server.application.dto.AssetDTO;
import io.haechi.sample.server.application.dto.TransformResponseDTO;
import io.haechi.sample.server.application.dto.WalletDTO;
import io.haechi.sample.server.domain.value.Asset;
import io.haechi.sample.server.domain.value.Token;
import io.haechi.sample.server.domain.Wallet;
import io.haechi.sample.server.domain.WalletService;
import io.haechi.sample.server.domain.value.HenesisTokenSymbol;
import io.haechi.sample.server.domain.value.TransactionReceipt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class WalletApplicationServiceTest {
    @Autowired
    private WalletApplicationService walletApplicationService;

    @MockBean
    private WalletService walletService;

    @Test
    public void createWalletTest() {
        // given
        String userId = "sample-userId";
        Wallet expectedWallet = Wallet.builder()
                .id("39d3c541e513c49180a6f93babf75434")
                .userId("sample-userId")
                .address("0x5fc41e1eef978a8ef69b455b67fb3920d0ad78df")
                .build();
        when(walletService.createUserWallet(userId)).thenReturn(expectedWallet);

        // when
        WalletDTO walletDTO = walletApplicationService.createUserWallet(userId);

        // then
        assertThat(walletDTO.getId()).isEqualTo(expectedWallet.getId());
        assertThat(walletDTO.getAddress()).isEqualTo(expectedWallet.getAddress());
        assertThat(walletDTO.getUserId()).isEqualTo(expectedWallet.getUserId());
    }

    @Test
    public void findWalletByWalletIdTest() {
        // given
        String walletId = "39d3c541e513c49180a6f93babf75434";
        Wallet expectedWallet = Wallet.builder()
                .id("39d3c541e513c49180a6f93babf75434")
                .userId("sample-userId")
                .address("0x5fc41e1eef978a8ef69b455b67fb3920d0ad78df")
                .build();
        when(walletService.findWalletByWalletId(walletId)).thenReturn(expectedWallet);

        // when
        WalletDTO walletDTO = walletApplicationService.findWalletByWalletId(walletId);

        // then
        assertThat(walletDTO.getId()).isEqualTo(expectedWallet.getId());
        assertThat(walletDTO.getAddress()).isEqualTo(expectedWallet.getAddress());
        assertThat(walletDTO.getUserId()).isEqualTo(expectedWallet.getUserId());
    }

    @Test
    public void findAssetByWalletIdAndTokenSymbolTest() {
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
        when(walletService.findAssetByWalletIdAndTokenSymbol(walletId, HenesisTokenSymbol.EVT.toString())).thenReturn(expectedAsset);

        // when
        AssetDTO assetDTO = walletApplicationService.findAssetByWalletIdAndTokenSymbol(walletId, HenesisTokenSymbol.EVT.toString());

        // then
        assertThat(assetDTO.getName()).isEqualTo(expectedAsset.getName());
        assertThat(assetDTO.getAmount()).isEqualTo(expectedAsset.getToken().getAmount());
        assertThat(assetDTO.getSymbol()).isEqualTo(expectedAsset.getToken().getSymbol());
    }

}
