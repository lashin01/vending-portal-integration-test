package com.flagship.gvmp.vending_portal_integration_tests.tests.products;

import com.flagship.gvmp.vending_portal_integration_tests.base.BaseIntegrationTest;
import com.flagship.gvmp.vending_portal_integration_tests.clients.ProductClient;
import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import com.flagship.gvmp.vending_portal_integration_tests.dto.product.ProductDtos.CreateTmsProductRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ProductIntegrationTest extends BaseIntegrationTest {

    @Autowired
    ProductIntegrationTest(TestProperties testProperties) {
        super(testProperties);
    }

    @Test
    void shouldListProducts() {
        // Arrange
        ProductClient productClient = authenticatedProductClient();

        // Act
        Response response = productClient.getProducts();

        // Assert
        assertThat(response.statusCode())
                .isEqualTo(200);
        assertThat(response.jsonPath().getList("$"))
                .isNotNull();
    }

    @Test
    void shouldGetConfiguredExistingProduct() {
        // Arrange
        Long productId = requireConfiguredId(testProperties.getTmsProductId(), "Set TMS_TEST_TMS_PRODUCT_ID to run this test.");
        ProductClient productClient = authenticatedProductClient();

        // Act
        Response response = productClient.getProduct(productId);

        // Assert
        assertThat(response.statusCode())
                .isEqualTo(200);
        assertThat(response.jsonPath().getLong("id"))
                .isEqualTo(productId);
    }

    @Test
    void shouldCreateProductFromConfiguredGoldEraProductId() {
        // Arrange
        String goldEraProductId = requireConfiguredText(testProperties.getGoldEraProductId(), "Set TMS_TEST_GOLD_ERA_PRODUCT_ID to run this test.");
        ProductClient productClient = authenticatedProductClient();

        // Act
        Response response = productClient.createProduct(new CreateTmsProductRequest(goldEraProductId));

        // Assert
        assertThat(response.statusCode())
                .isIn(200, 201);
        assertThat(response.jsonPath().getLong("id"))
                .isNotNull();
        assertThat(response.jsonPath().getString("goldEraProductId"))
                .isEqualTo(goldEraProductId);
    }

    @Test
    void shouldDeleteConfiguredExistingProduct() {
        // Arrange
        Long productId = requireConfiguredId(testProperties.getTmsProductId(), "Set TMS_TEST_TMS_PRODUCT_ID to a deletable product id to run this test.");
        ProductClient productClient = authenticatedProductClient();

        // Act
        Response response = productClient.deleteProduct(productId);

        // Assert
        assertThat(response.statusCode())
                .isBetween(200, 299);
    }

    @Test
    void shouldListAvailableEcommerceProducts() {
        // Arrange
        ProductClient productClient = authenticatedProductClient();

        // Act
        Response response = productClient.getAvailableEcommerceProducts();

        // Assert
        assertThat(response.statusCode())
                .isEqualTo(200);
        assertThat(response.jsonPath().getList("$"))
                .isNotNull();
    }

    @Test
    void shouldSearchAvailableEcommerceProductsByQuery() {
        // Arrange
        String query = requireConfiguredText(testProperties.getGoldEraProductId(), "Set TMS_TEST_GOLD_ERA_PRODUCT_ID to run this test.");
        ProductClient productClient = authenticatedProductClient();

        // Act
        Response response = productClient.getAvailableEcommerceProducts(query);

        // Assert
        assertThat(response.statusCode())
                .isEqualTo(200);
        assertThat(response.jsonPath().getList("$"))
                .isNotNull();
    }

    private ProductClient authenticatedProductClient() {
        return new ProductClient(authenticate());
    }

    private static Long requireConfiguredId(Long id, String message) {
        Assumptions.assumeTrue(id != null, message);
        return id;
    }

    private static String requireConfiguredText(String value, String message) {
        Assumptions.assumeTrue(value != null && !value.isBlank(), message);
        return value;
    }
}
