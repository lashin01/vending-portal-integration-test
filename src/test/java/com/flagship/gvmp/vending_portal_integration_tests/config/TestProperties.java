package com.flagship.gvmp.vending_portal_integration_tests.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestProperties {

    @Value("${base.url}")
    private String baseUrl;

    @Value("${test.username}")
    private String username;

    @Value("${test.password}")
    private String password;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
