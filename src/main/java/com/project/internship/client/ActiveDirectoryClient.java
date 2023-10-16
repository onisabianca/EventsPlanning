package com.project.internship.client;

import com.project.internship.client.model.EmployeeActiveDirectory;
import com.project.internship.exception.InvalidCredentialsException;
import com.project.internship.helper.OAuthHelper;
import com.project.internship.model.Credentials;
import com.project.internship.model.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ActiveDirectoryClient {
    private final OAuthHelper oAuthHelper;
    private final RestTemplate restTemplate;
    private final ConversionService conversionService;

    @Value("${services.active-directory-business.url}")
    private String activeDirectoryUrl;

    public ActiveDirectoryClient(OAuthHelper oAuthHelper, RestTemplate restTemplate, ConversionService conversionService) {
        this.oAuthHelper = oAuthHelper;
        this.restTemplate = restTemplate;
        this.conversionService = conversionService;
    }

    public Employee authenticateUser(Credentials credentials) {
        HttpEntity<Credentials> credentialsHttpEntity = new HttpEntity<>(credentials, oAuthHelper.getHeaders());

        try {
            ResponseEntity<EmployeeActiveDirectory> employeeAD = restTemplate.exchange(activeDirectoryUrl, HttpMethod.POST, credentialsHttpEntity, EmployeeActiveDirectory.class);
            return conversionService.convert(employeeAD.getBody(), Employee.class);
        } catch (HttpClientErrorException.Unauthorized e) {
            throw new InvalidCredentialsException("The credentials are invalid!");
        }
    }
}
