package io.haechi.sample.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app")
public class BlockchainProperties {
    private String rpcEndpoint;
}
