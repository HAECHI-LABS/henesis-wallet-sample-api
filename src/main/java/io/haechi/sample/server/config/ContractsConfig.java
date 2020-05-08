package io.haechi.sample.server.config;

import io.haechi.sample.server.domain.value.Address;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ContractsProperties.class})
public class ContractsConfig {
    @Bean
    public ContractsProperties contractsProperties() {
        return new ContractsProperties();
    }

    @Bean
    @Qualifier("ownableAddress")
    public Address ownableAddress(ContractsProperties contractsProperties) {
        return new Address(contractsProperties.getOwnableAddress());
    }
}
