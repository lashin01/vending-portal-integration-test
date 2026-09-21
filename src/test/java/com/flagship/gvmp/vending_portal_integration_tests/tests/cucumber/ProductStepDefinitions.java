package com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber;

import com.flagship.gvmp.vending_portal_integration_tests.clients.ProductClient;
import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import com.flagship.gvmp.vending_portal_integration_tests.auth.AccessTokenProvider;
import com.flagship.gvmp.vending_portal_integration_tests.clients.AuthClient;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.configureRestAssured;
import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.requireBackendReachable;
import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.requireId;
import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.skip;
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
        requireId(testProperties.getTmsProductId(), "Set TMS_TEST_TMS_PRODUCT_ID to an existing backend product id.");
    }

    @Given("valid product creation data is configured")
    public void validProductCreationDataIsConfigured() {
        skip("Product creation request DTO is not defined in this integration-test project yet.");
    }

    @Given("an existing deletable product id is configured")
    public void anExistingDeletableProductIdIsConfigured() {
        requireId(testProperties.getTmsProductId(), "Set TMS_TEST_TMS_PRODUCT_ID to an existing deletable backend product id.");
    }

    @When("I list products")
    public void iListProducts() {
        context.setResponse(productClient().getProducts());
    }

    @When("I get the configured product")
    public void iGetTheConfiguredProduct() {
        context.setResponse(productClient().getProduct(testProperties.getTmsProductId()));
    }

    @When("I create a product")
    public void iCreateAProduct() {
        skip("Product creation request DTO is not defined in this integration-test project yet.");
    }

    @When("I delete the configured product")
    public void iDeleteTheConfiguredProduct() {
        context.setResponse(productClient().deleteProduct(testProperties.getTmsProductId()));
    }

    @When("I list ecommerce-available products")
    public void iListEcommerceAvailableProducts() {
        context.setResponse(productClient().getAvailableEcommerceProducts());
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
    @Then("the response should describe the created product")
    public void theResponseShouldDescribeAProduct() {
        assertThat(context.getResponse().jsonPath().getLong("id"))
                .isNotNull();
    }

    private ProductClient productClient() {
        return new ProductClient(context.getAccessToken());
    }
}
