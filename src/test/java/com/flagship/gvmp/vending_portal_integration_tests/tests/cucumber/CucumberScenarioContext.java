package com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber;

import io.cucumber.spring.ScenarioScope;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
public class CucumberScenarioContext {

    private String accessToken;
    private Response response;
    private Long currentOrderId;
    private Long currentBoxId;
    private Long currentDocumentId;
    private Long currentMachineId;
    private Long currentProductId;
    private String currentGoldEraProductId;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Long getCurrentOrderId() {
        return currentOrderId;
    }

    public void setCurrentOrderId(Long currentOrderId) {
        this.currentOrderId = currentOrderId;
    }

    public Long getCurrentBoxId() {
        return currentBoxId;
    }

    public void setCurrentBoxId(Long currentBoxId) {
        this.currentBoxId = currentBoxId;
    }

    public Long getCurrentDocumentId() {
        return currentDocumentId;
    }

    public void setCurrentDocumentId(Long currentDocumentId) {
        this.currentDocumentId = currentDocumentId;
    }

    public Long getCurrentMachineId() {
        return currentMachineId;
    }

    public void setCurrentMachineId(Long currentMachineId) {
        this.currentMachineId = currentMachineId;
    }

    public Long getCurrentProductId() {
        return currentProductId;
    }

    public void setCurrentProductId(Long currentProductId) {
        this.currentProductId = currentProductId;
    }

    public String getCurrentGoldEraProductId() {
        return currentGoldEraProductId;
    }

    public void setCurrentGoldEraProductId(String currentGoldEraProductId) {
        this.currentGoldEraProductId = currentGoldEraProductId;
    }
}
