package com.apache.camel.poc.config;


import io.lakefs.clients.sdk.ApiClient;
import io.lakefs.clients.sdk.auth.HttpBasicAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LakeFsClientConfig {


    @Bean
    public ApiClient lakeFsApiClient(
            @Value("${lakefs.endpoint}") String endpoint,
            @Value("${lakefs.access-key}") String accessKey,
            @Value("${lakefs.secret-key}") String secretKey
    ) {

        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(endpoint + "/api/v1");
        HttpBasicAuth basicAuth = (HttpBasicAuth) apiClient.getAuthentication("basic_auth");
        basicAuth.setUsername(accessKey);
        basicAuth.setPassword(secretKey);
        return apiClient;
    }
}