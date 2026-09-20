package com.flagship.gvmp.vending_portal_integration_tests.auth;

import com.flagship.gvmp.vending_portal_integration_tests.clients.AuthClient;
import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import io.restassured.response.Response;

public class AccessTokenProvider {

    private final AuthClient authClient;
    private final TestProperties testProperties;

    public AccessTokenProvider(AuthClient authClient, TestProperties testProperties) {
        this.authClient = authClient;
        this.testProperties = testProperties;
    }

    public String getAccessToken() {
        requireCredential("TMS_TEST_USERNAME", testProperties.getUsername());
        requireCredential("TMS_TEST_PASSWORD", testProperties.getPassword());

        Response response = authClient.login(
                testProperties.getUsername(),
                testProperties.getPassword()
        );

        response.then().statusCode(200);

        return response.jsonPath()
                .getString("accessToken");
    }

    private static void requireCredential(String environmentVariableName, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing test credential. Set " + environmentVariableName + " before running integration tests."
            );
        }
    }
}
