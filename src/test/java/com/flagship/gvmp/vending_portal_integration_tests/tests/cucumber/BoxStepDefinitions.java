package com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber;

import com.flagship.gvmp.vending_portal_integration_tests.clients.BoxClient;
import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import com.flagship.gvmp.vending_portal_integration_tests.auth.AccessTokenProvider;
import com.flagship.gvmp.vending_portal_integration_tests.clients.AuthClient;
import com.flagship.gvmp.vending_portal_integration_tests.dto.machine.BoxDtos.LinkProductRequest;
import com.flagship.gvmp.vending_portal_integration_tests.dto.machine.BoxDtos.UpdateBoxLabelRequest;
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

public class BoxStepDefinitions {

    private final TestProperties testProperties;
    private final CucumberScenarioContext context;
    private final AccessTokenProvider accessTokenProvider;

    @Autowired
    public BoxStepDefinitions(TestProperties testProperties, CucumberScenarioContext context) {
        this.testProperties = testProperties;
        this.context = context;
        this.accessTokenProvider = new AccessTokenProvider(new AuthClient(), testProperties);
    }

    @Before
    public void configureBoxRestAssured() {
        configureRestAssured(testProperties);
    }

    @Given("the box API is available")
    public void theBoxApiIsAvailable() {
        requireBackendReachable(testProperties);
    }

    @Given("a box admin access token is available")
    public void aBoxAdminAccessTokenIsAvailable() {
        context.setAccessToken(accessTokenProvider.getAccessToken());
    }

    @Given("an existing box id is configured")
    public void anExistingBoxIdIsConfigured() {
        context.setCurrentBoxId(requireId(testProperties.getBoxId(), "Set TMS_TEST_BOX_ID to an existing backend box id."));
    }

    @Given("a box label value is configured")
    public void aBoxLabelValueIsConfigured() {
        requireText(testProperties.getBoxLabel(), "Set TMS_TEST_BOX_LABEL to run this scenario.");
    }

    @Given("existing product identifiers are configured")
    public void existingProductIdentifiersAreConfigured() {
        requireId(testProperties.getTmsProductId(), "Set TMS_TEST_TMS_PRODUCT_ID to an existing backend product id.");
        requireText(testProperties.getGoldEraProductId(), "Set TMS_TEST_GOLD_ERA_PRODUCT_ID to run this scenario.");
    }

    @When("I update the configured box label")
    public void iUpdateTheConfiguredBoxLabel() {
        context.setResponse(boxClient().updateLabel(
                context.getCurrentBoxId(),
                new UpdateBoxLabelRequest(testProperties.getBoxLabel())
        ));
    }

    @When("I link the configured product to the box")
    public void iLinkTheConfiguredProductToTheBox() {
        context.setResponse(boxClient().linkProduct(
                context.getCurrentBoxId(),
                new LinkProductRequest(testProperties.getTmsProductId(), testProperties.getGoldEraProductId())
        ));
    }

    @When("I unlink the product from the configured box")
    public void iUnlinkTheProductFromTheConfiguredBox() {
        context.setResponse(boxClient().unlinkProduct(context.getCurrentBoxId()));
    }

    @When("I restock the configured box")
    public void iRestockTheConfiguredBox() {
        context.setResponse(boxClient().restock(context.getCurrentBoxId()));
    }

    @When("I mark the configured box empty")
    public void iMarkTheConfiguredBoxEmpty() {
        context.setResponse(boxClient().markEmpty(context.getCurrentBoxId()));
    }

    @When("I mark the configured box faulty")
    public void iMarkTheConfiguredBoxFaulty() {
        context.setResponse(boxClient().markFault(context.getCurrentBoxId()));
    }

    @Then("the box response status should be {int}")
    public void theBoxResponseStatusShouldBe(int expectedStatusCode) {
        assertThat(context.getResponse().statusCode())
                .isEqualTo(expectedStatusCode);
    }

    @Then("the response should describe the configured box")
    public void theResponseShouldDescribeTheConfiguredBox() {
        assertThat(context.getResponse().jsonPath().getLong("id"))
                .isEqualTo(context.getCurrentBoxId());
    }

    private BoxClient boxClient() {
        return new BoxClient(context.getAccessToken());
    }
}
