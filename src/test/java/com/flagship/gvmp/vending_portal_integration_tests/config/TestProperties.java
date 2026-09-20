package com.flagship.gvmp.vending_portal_integration_tests.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestProperties {

    private final String baseUrl;
    private final String username;
    private final String password;
    private final Long boxId;
    private final String boxLabel;
    private final Long tmsProductId;
    private final String goldEraProductId;
    private final Long machineId;
    private final Long orderId;
    private final Long orderDocumentId;
    private final String createOrderRequestJson;
    private final Long markPaidOrderId;
    private final Long dispenseOrderId;
    private final Long completeOrderId;
    private final Long failOrderId;
    private final Long cancelOrderId;

    public TestProperties(
            @Value("${tms.base-url}") String baseUrl,
            @Value("${tms.test.username:}") String username,
            @Value("${tms.test.password:}") String password,
            @Value("${tms.test.box-id:}") String boxId,
            @Value("${tms.test.box-label:}") String boxLabel,
            @Value("${tms.test.tms-product-id:}") String tmsProductId,
            @Value("${tms.test.gold-era-product-id:}") String goldEraProductId,
            @Value("${tms.test.machine-id:}") String machineId,
            @Value("${tms.test.order-id:}") String orderId,
            @Value("${tms.test.order-document-id:}") String orderDocumentId,
            @Value("${tms.test.create-order-request-json:}") String createOrderRequestJson,
            @Value("${tms.test.mark-paid-order-id:}") String markPaidOrderId,
            @Value("${tms.test.dispense-order-id:}") String dispenseOrderId,
            @Value("${tms.test.complete-order-id:}") String completeOrderId,
            @Value("${tms.test.fail-order-id:}") String failOrderId,
            @Value("${tms.test.cancel-order-id:}") String cancelOrderId
    ) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
        this.boxId = parseLong(boxId);
        this.boxLabel = boxLabel;
        this.tmsProductId = parseLong(tmsProductId);
        this.goldEraProductId = goldEraProductId;
        this.machineId = parseLong(machineId);
        this.orderId = parseLong(orderId);
        this.orderDocumentId = parseLong(orderDocumentId);
        this.createOrderRequestJson = createOrderRequestJson;
        this.markPaidOrderId = parseLong(markPaidOrderId);
        this.dispenseOrderId = parseLong(dispenseOrderId);
        this.completeOrderId = parseLong(completeOrderId);
        this.failOrderId = parseLong(failOrderId);
        this.cancelOrderId = parseLong(cancelOrderId);
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Long getBoxId() {
        return boxId;
    }

    public String getBoxLabel() {
        return boxLabel;
    }

    public Long getTmsProductId() {
        return tmsProductId;
    }

    public String getGoldEraProductId() {
        return goldEraProductId;
    }

    public Long getMachineId() {
        return machineId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getOrderDocumentId() {
        return orderDocumentId;
    }

    public String getCreateOrderRequestJson() {
        return createOrderRequestJson;
    }

    public Long getMarkPaidOrderId() {
        return markPaidOrderId;
    }

    public Long getDispenseOrderId() {
        return dispenseOrderId;
    }

    public Long getCompleteOrderId() {
        return completeOrderId;
    }

    public Long getFailOrderId() {
        return failOrderId;
    }

    public Long getCancelOrderId() {
        return cancelOrderId;
    }

    private static Long parseLong(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return Long.valueOf(value);
    }
}
