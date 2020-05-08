package io.haechi.sample.server.config;

import io.haechi.sample.server.infra.auth.HMACService;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

@Configuration
public class RestTemplateConfig {
    // Determines the timeout in milliseconds until a connection is established.
    private static final int CONNECT_TIMEOUT = 30000;
    // The timeout when requesting a connection from the connection manager.
    private static final int REQUEST_TIMEOUT = 30000;

    private final String walletAccessToken;
    private final String walletApiSecret;
    private final String enclaveUrl;
    private final String masterWalletId;
    private final String walletUrl;

    @Autowired
    private HMACService hmacService;

    public RestTemplateConfig(
            @Qualifier("walletAccessToken") String walletAccessToken,
            @Qualifier("walletApiSecret") String walletApiSecret,
            @Qualifier("enclaveUrl") String enclaveUrl,
            @Qualifier("masterWalletId") String masterWalletId,
            @Qualifier("walletUrl") String walletUrl
    ) {
        this.walletAccessToken = walletAccessToken;
        this.walletApiSecret = walletApiSecret;
        this.enclaveUrl = enclaveUrl;
        this.masterWalletId = masterWalletId;
        this.walletUrl = walletUrl;
    }

    @Bean
    public RestTemplate defaultRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory());
        return restTemplate;
    }

    @Bean
    @Qualifier("enclaveClient")
    public RestTemplate enclaveRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(
                String.format("%s/api/v1/wallets/%s", enclaveUrl, masterWalletId)));
        restTemplate.setInterceptors(
                Collections.singletonList(new HeaderInterceptor())
        );
        return restTemplate;
    }

    @Bean
    @Qualifier("walletClient")
    public RestTemplate walletRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(
                String.format("%s/api/v1", walletUrl)));
        restTemplate.setInterceptors(
                Collections.singletonList(new HeaderInterceptor())
        );
        return restTemplate;
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setHttpClient(httpClient());
        return clientHttpRequestFactory;
    }

    private CloseableHttpClient httpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .build();

        return HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    private class HeaderInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            HttpHeaders headers = request.getHeaders();
            String timestamp = String.valueOf(new Date().getTime());
            headers.add("X-Henesis-Secret", walletApiSecret);
            headers.add("X-Henesis-Timestamp", timestamp);
            headers.add("X-Henesis-Signature", createSignature(request, body, timestamp));
            headers.add("Authorization", "Bearer " + walletAccessToken);
            return execution.execute(request, body);
        }
    }

    private String createSignature(HttpRequest request, byte[] body, String timestamp) {
        String path = request.getURI().toString();
        String msg = request.getMethod() + path + new String(body) + timestamp;
        return hmacService.calculateHMAC(walletApiSecret, msg);
    }
}
