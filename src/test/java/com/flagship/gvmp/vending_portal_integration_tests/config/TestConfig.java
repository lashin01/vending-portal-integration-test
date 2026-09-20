package com.flagship.gvmp.vending_portal_integration_tests.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public String baseUrl(
            @Value("${base.url}") String baseUrl
    ) {
        return baseUrl;
    }
}