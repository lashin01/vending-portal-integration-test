package com.flagship.gvmp.vending_portal_integration_tests.base;

import com.flagship.gvmp.vending_portal_integration_tests.auth.AccessTokenProvider;
import com.flagship.gvmp.vending_portal_integration_tests.clients.AuthClient;
import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class BaseIntegrationTest {

    protected final TestProperties testProperties;
    protected final AuthClient authClient;
    private final AccessTokenProvider accessTokenProvider;

    protected BaseIntegrationTest(TestProperties testProperties) {
        this.testProperties = testProperties;
        this.authClient = new AuthClient();
        this.accessTokenProvider = new AccessTokenProvider(authClient, testProperties);
    }

    @BeforeEach
    void configureRestAssured() {
        RestAssured.baseURI = testProperties.getBaseUrl();
    }

    protected String authenticate() {
        return accessTokenProvider.getAccessToken();
    }
}
