package io.haechi.sample.server.config;

import io.haechi.sample.server.config.util.EthSigner;
import io.haechi.sample.server.domain.value.Address;
import io.haechi.sample.server.infra.contracts.Ownable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

@Configuration
public class BlockchainConfig {
    @Bean
    public Web3j web3j(@Qualifier("rpcEndpoint") String rpcEndpoint) {
        return Web3j.build(new HttpService(rpcEndpoint));
    }

    @Bean
    public Ownable ownable(
            Web3j web3j,
            @Qualifier("ownableAddress") Address ownableAddress
    ) {
        return Ownable.load(
                ownableAddress.getValue(),
                web3j,
                EthSigner.getDummyCredentials(),
                new DefaultGasProvider()
        );
    }

    @Bean
    public BlockchainProperties blockchainProperties() {
        return new BlockchainProperties();
    }
    @Bean
    @Qualifier("rpcEnpoint")
    public String rpcEndpoint(BlockchainProperties blockchainProperties) {
        return blockchainProperties.getRpcEndpoint();
    }

}
