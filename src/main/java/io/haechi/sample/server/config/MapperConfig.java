package io.haechi.sample.server.config;

import io.haechi.sample.server.application.dto.AssetDTO;
import io.haechi.sample.server.application.dto.TransactionResponseDTO;
import io.haechi.sample.server.domain.Transaction;
import io.haechi.sample.server.domain.value.Asset;
import io.haechi.sample.server.domain.value.TransactionStatus;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    Converter<TransactionStatus, String> transactionStatusStringConverter = mappingContext -> {
        if(mappingContext.getSource() != null) {
            return mappingContext.getSource().getName();
        }
        return null;
    };

    @Bean
    public PropertyMap<Asset, AssetDTO> assetToAssetDTOPropertyMap() {
        return new PropertyMap<Asset, AssetDTO>() {
            @Override
            protected void configure() {
                map().setName(source.getName());
                map().setSymbol(source.getToken().getSymbol());
                map().setAmount(source.getToken().getAmount());
            }
        };
    }

    @Bean
    public PropertyMap<Transaction, TransactionResponseDTO> transactionTransactionResponseDTOPropertyMap() {
        return new PropertyMap<Transaction, TransactionResponseDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setFrom(source.getFrom().getValue());
                map().setTo(source.getTo().getValue());
                map().setHash(source.getHash().getValue());
                // transaction status is set to default strategy
                map().setError(source.getError());
            }
        };
    }

    @Bean
    public ModelMapper modelMapper(
            PropertyMap<Asset, AssetDTO> assetToAssetDTOPropertyMap,
            PropertyMap<Transaction, TransactionResponseDTO> transactionTransactionResponseDTOPropertyMap
    ) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(assetToAssetDTOPropertyMap);
        modelMapper.addMappings(transactionTransactionResponseDTOPropertyMap);
        return modelMapper;
    }
}
