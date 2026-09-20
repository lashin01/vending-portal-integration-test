package com.flagship.gvmp.vending_portal_integration_tests.clients;

import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ProductClient {

    private static final String PRODUCTS_ENDPOINT = "/api/admin/products";

    private final String accessToken;

    public ProductClient(String accessToken) {
        this.accessToken = accessToken;
    }

    public Response getProducts() {
        return authenticated()
                .when()
                .get(PRODUCTS_ENDPOINT);
    }

    public Response getProduct(Long productId) {
        return authenticated()
                .when()
                .get(PRODUCTS_ENDPOINT + "/" + productId);
    }

    public Response createProduct(Object request) {
        return authenticated()
                .contentType("application/json")
                .body(request)
                .when()
                .post(PRODUCTS_ENDPOINT);
    }

    public Response deleteProduct(Long productId) {
        return authenticated()
                .when()
                .delete(PRODUCTS_ENDPOINT + "/" + productId);
    }

    public Response getAvailableEcommerceProducts() {
        return authenticated()
                .when()
                .get(PRODUCTS_ENDPOINT + "/ecommerce/available");
    }

    private RequestSpecification authenticated() {
        return given()
                .header("Authorization", "Bearer " + accessToken);
    }
}
