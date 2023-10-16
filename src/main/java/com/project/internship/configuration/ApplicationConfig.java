package com.project.internship.configuration;

import com.project.services.oauth.OAuthTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfig {

    @Value("${services.oauth.primary-api-host}")
    private String primaryOauthHost;

    @Bean
    public RestTemplate restTemplateBean() {
        return new RestTemplate();
    }

    @Bean
    public OAuthTokenProvider tokenProvider() {
        return new OAuthTokenProvider(primaryOauthHost);
    }
}
