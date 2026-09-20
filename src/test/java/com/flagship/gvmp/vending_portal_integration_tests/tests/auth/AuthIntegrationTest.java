package com.flagship.gvmp.vending_portal_integration_tests.tests.auth;

import com.flagship.gvmp.vending_portal_integration_tests.base.BaseIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

class AuthIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldLoginSuccessfully() {

        String requestBody = """
                {
                    "username": "tms-integration-test",
                    "password": "TestPassword123!"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/public/auth/login")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("expiresIn", notNullValue())
                .body("tokenType", equalTo("Bearer"));
    }
}
