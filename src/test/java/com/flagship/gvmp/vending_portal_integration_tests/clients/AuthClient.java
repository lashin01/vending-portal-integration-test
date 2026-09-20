package com.flagship.gvmp.vending_portal_integration_tests.clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthClient {

    private static final String LOGIN_ENDPOINT = "/api/public/auth/login";

    public Response login(String username, String password) {
        return given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "username", username,
                        "password", password
                ))
                .when()
                .post(LOGIN_ENDPOINT);
    }
}
