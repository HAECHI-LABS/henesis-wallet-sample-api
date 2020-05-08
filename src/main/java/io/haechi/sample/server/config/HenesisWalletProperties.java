package io.haechi.sample.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.henesis-wallet")
public class HenesisWalletProperties {
    private String url;

    private String apiSecret;

    private String accessToken;

    private String masterWalletId;

    private String masterWalletAddress;

    private String passphrase;

    private String enclaveUrl;
}
