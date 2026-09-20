package com.flagship.gvmp.vending_portal_integration_tests.clients;

import com.flagship.gvmp.vending_portal_integration_tests.dto.machine.BoxDtos.LinkProductRequest;
import com.flagship.gvmp.vending_portal_integration_tests.dto.machine.BoxDtos.UpdateBoxLabelRequest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BoxClient {

    private static final String BOXES_ENDPOINT = "/api/admin/boxes";

    private final String accessToken;

    public BoxClient(String accessToken) {
        this.accessToken = accessToken;
    }

    public Response updateLabel(
            Long boxId,
            UpdateBoxLabelRequest request
    ) {
        return authenticated()
                .contentType("application/json")
                .body(request)
                .when()
                .patch(BOXES_ENDPOINT + "/" + boxId + "/label");
    }

    public Response linkProduct(
            Long boxId,
            LinkProductRequest request
    ) {
        return authenticated()
                .contentType("application/json")
                .body(request)
                .when()
                .patch(BOXES_ENDPOINT + "/" + boxId + "/link-product");
    }

    public Response unlinkProduct(Long boxId) {
        return authenticated()
                .when()
                .post(BOXES_ENDPOINT + "/" + boxId + "/unlink-product");
    }

    public Response restock(Long boxId) {
        return authenticated()
                .when()
                .post(BOXES_ENDPOINT + "/" + boxId + "/restock");
    }

    public Response markEmpty(Long boxId) {
        return authenticated()
                .when()
                .post(BOXES_ENDPOINT + "/" + boxId + "/mark-empty");
    }

    public Response markFault(Long boxId) {
        return authenticated()
                .when()
                .post(BOXES_ENDPOINT + "/" + boxId + "/mark-fault");
    }

    private RequestSpecification authenticated() {
        return given()
                .header("Authorization", "Bearer " + accessToken);
    }
}
