package com.flagship.gvmp.vending_portal_integration_tests.tests.orders;

import com.flagship.gvmp.vending_portal_integration_tests.base.BaseIntegrationTest;
import com.flagship.gvmp.vending_portal_integration_tests.clients.OrderClient;
import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import com.flagship.gvmp.vending_portal_integration_tests.dto.order.OrderDtos.CreateOrderRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class OrderIntegrationTest extends BaseIntegrationTest {

    @Autowired
    OrderIntegrationTest(TestProperties testProperties) {
        super(testProperties);
    }

    @Test
    void shouldListOrders() {
        // Arrange
        OrderClient orderClient = authenticatedOrderClient();

        // Act
        Response response = orderClient.listOrders();

        // Assert
        assertThat(response.statusCode())
                .isEqualTo(200);
        assertThat(response.jsonPath().getList("$"))
                .isNotNull();
    }

    @Test
    void shouldListOrdersByConfiguredMachine() {
        // Arrange
        Long machineId = requireConfiguredId(testProperties.getMachineId(), "Set TMS_TEST_MACHINE_ID to run this test.");
        OrderClient orderClient = authenticatedOrderClient();

        // Act
        Response response = orderClient.listOrders(machineId);

        // Assert
        assertThat(response.statusCode())
                .isEqualTo(200);
        assertThat(response.jsonPath().getList("$"))
                .isNotNull();
    }

    @Test
    void shouldGetConfiguredExistingOrder() {
        // Arrange
        Long orderId = requireConfiguredOrderId();
        OrderClient orderClient = authenticatedOrderClient();

        // Act
        Response response = orderClient.getOrder(orderId);

        // Assert
        assertSuccessfulOrderResponse(response, orderId);
    }

    @Test
    void shouldListDocumentsForConfiguredExistingOrder() {
        // Arrange
        Long orderId = requireConfiguredOrderId();
        OrderClient orderClient = authenticatedOrderClient();

        // Act
        Response response = orderClient.listDocuments(orderId);

        // Assert
        assertThat(response.statusCode())
                .isEqualTo(200);
        assertThat(response.jsonPath().getList("$"))
                .isNotNull();
    }

    @Test
    void shouldGetConfiguredExistingOrderDocument() {
        // Arrange
        Long orderId = requireConfiguredOrderId();
        Long documentId = requireConfiguredId(
                testProperties.getOrderDocumentId(),
                "Set TMS_TEST_ORDER_DOCUMENT_ID to run this test."
        );
        OrderClient orderClient = authenticatedOrderClient();

        // Act
        Response response = orderClient.getDocument(orderId, documentId);

        // Assert
        assertThat(response.statusCode())
                .isEqualTo(200);
        assertThat(response.getBody().asByteArray())
                .isNotEmpty();
    }

    @Test
    void shouldCreateOrderFromConfiguredDto() {
        // Arrange
        Assumptions.assumeTrue(
                testProperties.getMachineId() != null,
                "Set TMS_TEST_MACHINE_ID to run this test."
        );
        OrderClient orderClient = authenticatedOrderClient();
        CreateOrderRequest request = new CreateOrderRequest(
                testProperties.getMachineId(),
                testProperties.getTmsProductId(),
                testProperties.getGoldEraProductId()
        );

        // Act
        Response response = orderClient.create(request);

        // Assert
        assertThat(response.statusCode())
                .isEqualTo(200);
        assertThat(response.jsonPath().getLong("id"))
                .isNotNull();
    }

    @Test
    void shouldMarkConfiguredOrderPaid() {
        assertOrderStateTransition(testProperties.getMarkPaidOrderId(), OrderClient::markPaid);
    }

    @Test
    void shouldDispenseConfiguredOrder() {
        assertOrderStateTransition(testProperties.getDispenseOrderId(), OrderClient::dispense);
    }

    @Test
    void shouldCompleteConfiguredOrder() {
        assertOrderStateTransition(testProperties.getCompleteOrderId(), OrderClient::complete);
    }

    @Test
    void shouldFailConfiguredOrder() {
        assertOrderStateTransition(testProperties.getFailOrderId(), OrderClient::fail);
    }

    @Test
    void shouldCancelConfiguredOrder() {
        assertOrderStateTransition(testProperties.getCancelOrderId(), OrderClient::cancel);
    }

    private void assertOrderStateTransition(Long orderId, OrderAction orderAction) {
        // Arrange
        Long configuredOrderId = requireConfiguredId(orderId, "Set the matching TMS_TEST_*_ORDER_ID to run this test.");
        OrderClient orderClient = authenticatedOrderClient();

        // Act
        Response response = orderAction.execute(orderClient, configuredOrderId);

        // Assert
        assertSuccessfulOrderResponse(response, configuredOrderId);
    }

    private OrderClient authenticatedOrderClient() {
        return new OrderClient(authenticate());
    }

    private Long requireConfiguredOrderId() {
        return requireConfiguredId(testProperties.getOrderId(), "Set TMS_TEST_ORDER_ID to an existing backend order id to run this test.");
    }

    private static Long requireConfiguredId(Long id, String message) {
        Assumptions.assumeTrue(id != null, message);
        return id;
    }

    private static void assertSuccessfulOrderResponse(Response response, Long expectedOrderId) {
        assertThat(response.statusCode())
                .isEqualTo(200);
        assertThat(response.jsonPath().getLong("id"))
                .isEqualTo(expectedOrderId);
    }

    @FunctionalInterface
    private interface OrderAction {

        Response execute(OrderClient orderClient, Long orderId);
    }
}
