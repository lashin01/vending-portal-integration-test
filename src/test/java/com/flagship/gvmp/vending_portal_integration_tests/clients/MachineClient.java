package com.flagship.gvmp.vending_portal_integration_tests.clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class MachineClient {

    private static final String MACHINES_ENDPOINT =
            "/api/admin/machines";

    public Response getMachines(String accessToken) {

        return given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get(MACHINES_ENDPOINT);
    }

    public Response getMachine(
            Long machineId,
            String accessToken
    ) {

        return given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get(MACHINES_ENDPOINT + "/" + machineId);
    }

    public Response getTopology(
            Long machineId,
            String accessToken
    ) {

        return given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get(MACHINES_ENDPOINT + "/" + machineId + "/topology");
    }

    public Response getPairingOtpHistory(
            Long machineId,
            String accessToken
    ) {

        return given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get(
                        MACHINES_ENDPOINT
                                + "/"
                                + machineId
                                + "/pairing-otp/history"
                );
    }

    public Response getActivity(
            Long machineId,
            String accessToken
    ) {

        return given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get(
                        MACHINES_ENDPOINT
                                + "/"
                                + machineId
                                + "/activity"
                );
    }
}
