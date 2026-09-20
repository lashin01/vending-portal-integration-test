package com.flagship.gvmp.vending_portal_integration_tests.tests.products;

import com.flagship.gvmp.vending_portal_integration_tests.base.BaseIntegrationTest;
import com.flagship.gvmp.vending_portal_integration_tests.clients.ProductClient;
import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ProductIntegrationTest extends BaseIntegrationTest {

    @Autowired
    ProductIntegrationTest(TestProperties testProperties) {
        super(testProperties);
    }

    @Test
    void shouldGetProducts() {
        // Arrange
        String accessToken = authenticate();
        ProductClient productClient = new ProductClient(accessToken);

        // Act
        Response response = productClient.getProducts();

        // Assert
        assertThat(response.statusCode())
                .isEqualTo(200);
        assertThat(response.body())
                .isNotNull();
    }
}
