package com.project.internship.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class OAuthHelper {

    @Value("${social-events.oauth.client-id}")
    private String clientId;

    @Value("${social-events.oauth.client-secret}")
    private String clientSecret;

    private final OAuthTokenProvider tokenProvider;

    public OAuthHelper(OAuthTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenProvider.getAccessTokenByClientCredentials(clientId, clientSecret));

        return headers;
    }
}
