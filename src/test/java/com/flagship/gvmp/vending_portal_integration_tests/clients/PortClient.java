package com.flagship.gvmp.vending_portal_integration_tests.clients;

import com.flagship.gvmp.vending_portal_integration_tests.dto.port.PortDtos.UpdatePortRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PortClient {

    private static final String PORTS_ENDPOINT = "/api/admin/ports";

    public Response updatePort(Long portId, UpdatePortRequest request, String accessToken) {

        return given()
                .header(
                        "Authorization",
                        "Bearer " + accessToken
                )
                .contentType("application/json")
                .body(request)
                .when()
                .patch(
                        PORTS_ENDPOINT
                                + "/"
                                + portId
                );
    }

    public Response deletePort(Long portId, String accessToken) {
        return given()
                .header(
                        "Authorization",
                        "Bearer " + accessToken
                )
                .when()
                .delete(
                        PORTS_ENDPOINT
                                + "/"
                                + portId
                );
    }
}
