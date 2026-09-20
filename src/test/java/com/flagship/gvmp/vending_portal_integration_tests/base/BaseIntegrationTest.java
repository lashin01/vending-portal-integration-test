package com.flagship.gvmp.vending_portal_integration_tests.base;

import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
public abstract class BaseIntegrationTest {

    @Autowired
    protected TestProperties testProperties;

    protected String accessToken;

    @BeforeEach
    void setup() {

        RestAssured.baseURI = testProperties.getBaseUrl();

        accessToken = loginAndGetAccessToken();
    }

    private String loginAndGetAccessToken() {

        String username = testProperties.getUsername();
        String password = testProperties.getPassword();

        String requestBody = """
                {
                    "username": "%s",
                    "password": "%s"
                }
                """.formatted(username, password);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/public/auth/login");

        response.then()
                .statusCode(200);

        return response.jsonPath()
                .getString("accessToken");
    }
}
