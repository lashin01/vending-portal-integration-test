package com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber;

import com.flagship.gvmp.vending_portal_integration_tests.clients.ProductClient;
import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import com.flagship.gvmp.vending_portal_integration_tests.auth.AccessTokenProvider;
import com.flagship.gvmp.vending_portal_integration_tests.clients.AuthClient;
import com.flagship.gvmp.vending_portal_integration_tests.dto.product.ProductDtos.CreateTmsProductRequest;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.configureRestAssured;
import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.requireBackendReachable;
import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.requireId;
import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.requireText;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductStepDefinitions {

    private final TestProperties testProperties;
    private final CucumberScenarioContext context;
    private final AccessTokenProvider accessTokenProvider;

    @Autowired
    public ProductStepDefinitions(TestProperties testProperties, CucumberScenarioContext context) {
        this.testProperties = testProperties;
        this.context = context;
        this.accessTokenProvider = new AccessTokenProvider(new AuthClient(), testProperties);
    }

    @Before
    public void configureProductRestAssured() {
        configureRestAssured(testProperties);
    }

    @Given("the product API is available")
    public void theProductApiIsAvailable() {
        requireBackendReachable(testProperties);
    }

    @Given("a product admin access token is available")
    public void aProductAdminAccessTokenIsAvailable() {
        context.setAccessToken(accessTokenProvider.getAccessToken());
    }

    @Given("an existing product id is configured")
    public void anExistingProductIdIsConfigured() {
        context.setCurrentProductId(requireId(testProperties.getTmsProductId(), "Set TMS_TEST_TMS_PRODUCT_ID to an existing backend product id."));
    }

    @Given("valid product creation data is configured")
    public void validProductCreationDataIsConfigured() {
        requireText(testProperties.getGoldEraProductId(), "Set TMS_TEST_GOLD_ERA_PRODUCT_ID to create a product.");
        context.setCurrentGoldEraProductId(testProperties.getGoldEraProductId());
    }

    @Given("an existing deletable product id is configured")
    public void anExistingDeletableProductIdIsConfigured() {
        context.setCurrentProductId(requireId(testProperties.getTmsProductId(), "Set TMS_TEST_TMS_PRODUCT_ID to an existing deletable backend product id."));
    }

    @Given("an ecommerce product search query is configured")
    public void anEcommerceProductSearchQueryIsConfigured() {
        requireText(testProperties.getGoldEraProductId(), "Set TMS_TEST_GOLD_ERA_PRODUCT_ID to search available ecommerce products.");
        context.setCurrentGoldEraProductId(testProperties.getGoldEraProductId());
    }

    @When("I list products")
    public void iListProducts() {
        context.setResponse(productClient().getProducts());
    }

    @When("I get the configured product")
    public void iGetTheConfiguredProduct() {
        context.setResponse(productClient().getProduct(context.getCurrentProductId()));
    }

    @When("I create a product")
    public void iCreateAProduct() {
        context.setResponse(productClient().createProduct(new CreateTmsProductRequest(context.getCurrentGoldEraProductId())));
    }

    @When("I delete the configured product")
    public void iDeleteTheConfiguredProduct() {
        context.setResponse(productClient().deleteProduct(context.getCurrentProductId()));
    }

    @When("I list ecommerce-available products")
    public void iListEcommerceAvailableProducts() {
        context.setResponse(productClient().getAvailableEcommerceProducts());
    }

    @When("I search ecommerce-available products")
    public void iSearchEcommerceAvailableProducts() {
        context.setResponse(productClient().getAvailableEcommerceProducts(context.getCurrentGoldEraProductId()));
    }

    @Then("the product response status should be {int}")
    public void theProductResponseStatusShouldBe(int expectedStatusCode) {
        assertThat(context.getResponse().statusCode())
                .isEqualTo(expectedStatusCode);
    }

    @Then("the product response status should indicate successful creation")
    public void theProductResponseStatusShouldIndicateSuccessfulCreation() {
        assertThat(context.getResponse().statusCode())
                .isIn(200, 201);
    }

    @Then("the product response status should indicate success")
    public void theProductResponseStatusShouldIndicateSuccess() {
        assertThat(context.getResponse().statusCode())
                .isBetween(200, 299);
    }

    @Then("the response body should be a product collection")
    public void theResponseBodyShouldBeAProductCollection() {
        assertThat(context.getResponse().jsonPath().getList("$"))
                .isNotNull();
    }

    @Then("the response should describe the requested product")
    public void theResponseShouldDescribeTheRequestedProduct() {
        assertThat(context.getResponse().jsonPath().getLong("id"))
                .isEqualTo(context.getCurrentProductId());
    }

    @Then("the response should describe the created product")
    public void theResponseShouldDescribeTheCreatedProduct() {
        assertThat(context.getResponse().jsonPath().getLong("id"))
                .isNotNull();
        assertThat(context.getResponse().jsonPath().getString("goldEraProductId"))
                .isEqualTo(context.getCurrentGoldEraProductId());
    }

    private ProductClient productClient() {
        return new ProductClient(context.getAccessToken());
    }
}
