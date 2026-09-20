package com.flagship.gvmp.vending_portal_integration_tests.tests.products;

import com.flagship.gvmp.vending_portal_integration_tests.base.BaseIntegrationTest;
import com.flagship.gvmp.vending_portal_integration_tests.clients.ProductClient;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductIntegrationTest extends BaseIntegrationTest {

    private final ProductClient productClient = new ProductClient();

    @Test
    void shouldGetProducts() {

        Response response = productClient.getProducts(accessToken);

        assertThat(response.statusCode())
                .isEqualTo(200);
    }
}
