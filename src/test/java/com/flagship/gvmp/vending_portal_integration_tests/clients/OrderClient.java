package com.flagship.gvmp.vending_portal_integration_tests.clients;

import com.flagship.gvmp.vending_portal_integration_tests.dto.order.OrderDtos.CreateOrderRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String ORDERS_ENDPOINT = "/api/admin/orders";

    private final String accessToken;

    public OrderClient(String accessToken) {
        this.accessToken = accessToken;
    }

    public Response listOrders() {
        return authenticated()
                .when()
                .get(ORDERS_ENDPOINT);
    }

    public Response listOrders(Long machineId) {
        return authenticated()
                .queryParam("machineId", machineId)
                .when()
                .get(ORDERS_ENDPOINT);
    }

    public Response getOrder(Long orderId) {
        return authenticated()
                .when()
                .get(ORDERS_ENDPOINT + "/" + orderId);
    }

    public Response listDocuments(Long orderId) {
        return authenticated()
                .when()
                .get(ORDERS_ENDPOINT + "/" + orderId + "/documents");
    }

    public Response getDocument(Long orderId, Long documentId) {
        return authenticated()
                .when()
                .get(ORDERS_ENDPOINT + "/" + orderId + "/documents/" + documentId);
    }

    public Response create(String requestJson) {
        return authenticated()
                .contentType("application/json")
                .body(requestJson)
                .when()
                .post(ORDERS_ENDPOINT);
    }

    public Response create(CreateOrderRequest request) {
        return authenticated()
                .contentType("application/json")
                .body(request)
                .when()
                .post(ORDERS_ENDPOINT);
    }

    public Response markPaid(Long orderId) {
        return authenticated()
                .when()
                .post(ORDERS_ENDPOINT + "/" + orderId + "/mark-paid");
    }

    public Response dispense(Long orderId) {
        return authenticated()
                .when()
                .post(ORDERS_ENDPOINT + "/" + orderId + "/dispense");
    }

    public Response complete(Long orderId) {
        return authenticated()
                .when()
                .post(ORDERS_ENDPOINT + "/" + orderId + "/complete");
    }

    public Response fail(Long orderId) {
        return authenticated()
                .when()
                .post(ORDERS_ENDPOINT + "/" + orderId + "/fail");
    }

    public Response cancel(Long orderId) {
        return authenticated()
                .when()
                .post(ORDERS_ENDPOINT + "/" + orderId + "/cancel");
    }

    private RequestSpecification authenticated() {
        return given()
                .header("Authorization", "Bearer " + accessToken);
    }
}
