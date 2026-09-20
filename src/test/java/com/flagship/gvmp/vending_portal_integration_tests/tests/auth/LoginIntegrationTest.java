package com.flagship.gvmp.vending_portal_integration_tests.tests.auth;

import com.flagship.gvmp.vending_portal_integration_tests.base.BaseIntegrationTest;
import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class LoginIntegrationTest extends BaseIntegrationTest {

    @Autowired
    LoginIntegrationTest(TestProperties testProperties) {
        super(testProperties);
    }

    @Test
    void shouldLoginSuccessfully() {
        // Arrange
        requireConfiguredCredentials();

        // Act
        Response response = authClient.login(
                testProperties.getUsername(),
                testProperties.getPassword()
        );

        // Assert
        response.then()
                .statusCode(200)
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("expiresIn", notNullValue())
                .body("tokenType", equalTo("Bearer"));

        assertThat(response.jsonPath().getString("accessToken"))
                .isNotBlank();
    }

    private void requireConfiguredCredentials() {
        assertThat(testProperties.getUsername())
                .as("Set TMS_TEST_USERNAME before running integration tests.")
                .isNotBlank();
        assertThat(testProperties.getPassword())
                .as("Set TMS_TEST_PASSWORD before running integration tests.")
                .isNotBlank();
    }
}
