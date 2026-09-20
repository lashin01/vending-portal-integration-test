package com.flagship.gvmp.vending_portal_integration_tests.clients;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ProductClient {

    private static final String PRODUCTS_ENDPOINT = "/api/admin/products";

    public Response getProducts(String accessToken) {

        return given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/api/admin/products");
    }

    public Response getProduct(Long productId) {
        return given()
                .when()
                .get(PRODUCTS_ENDPOINT + "/" + productId);
    }

    public Response deleteProduct(Long productId) {
        return given()
                .when()
                .delete(PRODUCTS_ENDPOINT + "/" + productId);
    }
}
