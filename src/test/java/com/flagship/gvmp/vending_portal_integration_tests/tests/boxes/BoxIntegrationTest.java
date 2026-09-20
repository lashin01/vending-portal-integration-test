package com.flagship.gvmp.vending_portal_integration_tests.tests.boxes;

import com.flagship.gvmp.vending_portal_integration_tests.base.BaseIntegrationTest;
import com.flagship.gvmp.vending_portal_integration_tests.clients.BoxClient;
import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import com.flagship.gvmp.vending_portal_integration_tests.dto.machine.BoxDtos.LinkProductRequest;
import com.flagship.gvmp.vending_portal_integration_tests.dto.machine.BoxDtos.UpdateBoxLabelRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class BoxIntegrationTest extends BaseIntegrationTest {

    @Autowired
    BoxIntegrationTest(TestProperties testProperties) {
        super(testProperties);
    }

    @Test
    void shouldUpdateConfiguredExistingBoxLabel() {
        // Arrange
        Long boxId = requireConfiguredBoxId();
        Assumptions.assumeTrue(
                testProperties.getBoxLabel() != null && !testProperties.getBoxLabel().isBlank(),
                "Set TMS_TEST_BOX_LABEL to run this test."
        );
        BoxClient boxClient = authenticatedBoxClient();
        UpdateBoxLabelRequest request = new UpdateBoxLabelRequest(testProperties.getBoxLabel());

        // Act
        Response response = boxClient.updateLabel(boxId, request);

        // Assert
        assertSuccessfulBoxResponse(response, boxId);
    }

    @Test
    void shouldLinkProductToConfiguredExistingBox() {
        // Arrange
        Long boxId = requireConfiguredBoxId();
        Assumptions.assumeTrue(
                testProperties.getTmsProductId() != null,
                "Set TMS_TEST_TMS_PRODUCT_ID to an existing backend product id to run this test."
        );
        Assumptions.assumeTrue(
                testProperties.getGoldEraProductId() != null && !testProperties.getGoldEraProductId().isBlank(),
                "Set TMS_TEST_GOLD_ERA_PRODUCT_ID to run this test."
        );
        BoxClient boxClient = authenticatedBoxClient();
        LinkProductRequest request = new LinkProductRequest(
                testProperties.getTmsProductId(),
                testProperties.getGoldEraProductId()
        );

        // Act
        Response response = boxClient.linkProduct(boxId, request);

        // Assert
        assertSuccessfulBoxResponse(response, boxId);
    }

    @Test
    void shouldUnlinkProductFromConfiguredExistingBox() {
        // Arrange
        Long boxId = requireConfiguredBoxId();
        BoxClient boxClient = authenticatedBoxClient();

        // Act
        Response response = boxClient.unlinkProduct(boxId);

        // Assert
        assertSuccessfulBoxResponse(response, boxId);
    }

    @Test
    void shouldRestockConfiguredExistingBox() {
        // Arrange
        Long boxId = requireConfiguredBoxId();
        BoxClient boxClient = authenticatedBoxClient();

        // Act
        Response response = boxClient.restock(boxId);

        // Assert
        assertSuccessfulBoxResponse(response, boxId);
    }

    @Test
    void shouldMarkConfiguredExistingBoxEmpty() {
        // Arrange
        Long boxId = requireConfiguredBoxId();
        BoxClient boxClient = authenticatedBoxClient();

        // Act
        Response response = boxClient.markEmpty(boxId);

        // Assert
        assertSuccessfulBoxResponse(response, boxId);
    }

    @Test
    void shouldMarkConfiguredExistingBoxFault() {
        // Arrange
        Long boxId = requireConfiguredBoxId();
        BoxClient boxClient = authenticatedBoxClient();

        // Act
        Response response = boxClient.markFault(boxId);

        // Assert
        assertSuccessfulBoxResponse(response, boxId);
    }

    private BoxClient authenticatedBoxClient() {
        return new BoxClient(authenticate());
    }

    private Long requireConfiguredBoxId() {
        Assumptions.assumeTrue(
                testProperties.getBoxId() != null,
                "Set TMS_TEST_BOX_ID to an existing backend box id to run this test."
        );

        return testProperties.getBoxId();
    }

    private static void assertSuccessfulBoxResponse(Response response, Long expectedBoxId) {
        assertThat(response.statusCode())
                .isEqualTo(200);
        assertThat(response.jsonPath().getLong("id"))
                .isEqualTo(expectedBoxId);
    }
}
