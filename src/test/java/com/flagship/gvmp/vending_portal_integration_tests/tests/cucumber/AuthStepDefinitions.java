package com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber;

import com.flagship.gvmp.vending_portal_integration_tests.clients.AuthClient;
import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.configureRestAssured;
import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.requireBackendReachable;
import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.requireText;
import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.skip;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthStepDefinitions {

    private final TestProperties testProperties;
    private final CucumberScenarioContext context;
    private final AuthClient authClient = new AuthClient();

    @Autowired
    public AuthStepDefinitions(
            TestProperties testProperties,
            CucumberScenarioContext context
    ) {
        this.testProperties = testProperties;
        this.context = context;
    }

    @Before
    public void configureAuthRestAssured() {
        configureRestAssured(testProperties);
    }

    @Given("the auth API is available")
    public void theAuthApiIsAvailable() {
        requireBackendReachable(testProperties);
    }

    @Given("valid test credentials are configured")
    public void validTestCredentialsAreConfigured() {
        requireText(testProperties.getUsername(), "Set TMS_TEST_USERNAME to run this scenario.");
        requireText(testProperties.getPassword(), "Set TMS_TEST_PASSWORD to run this scenario.");
    }

    @Given("a valid refresh token is available")
    public void aValidRefreshTokenIsAvailable() {
        skip("Refresh-token flow is not wired yet. Capture refreshToken from login before enabling this scenario.");
    }

    @When("I login with the configured credentials")
    public void iLoginWithTheConfiguredCredentials() {
        context.setResponse(authClient.login(testProperties.getUsername(), testProperties.getPassword()));
    }

    @When("I refresh the access token")
    public void iRefreshTheAccessToken() {
        skip("Refresh-token request body is not defined in this integration-test project yet.");
    }

    @Then("the auth response status should be {int}")
    public void theAuthResponseStatusShouldBe(int expectedStatusCode) {
        assertThat(context.getResponse().statusCode())
                .isEqualTo(expectedStatusCode);
    }

    @Then("the response should contain a non-empty access token")
    public void theResponseShouldContainANonEmptyAccessToken() {
        assertThat(context.getResponse().jsonPath().getString("accessToken"))
                .isNotBlank();
    }

    @Then("the response should contain a non-empty refresh token")
    public void theResponseShouldContainANonEmptyRefreshToken() {
        assertThat(context.getResponse().jsonPath().getString("refreshToken"))
                .isNotBlank();
    }

    @Then("the token type should be {string}")
    public void theTokenTypeShouldBe(String tokenType) {
        assertThat(context.getResponse().jsonPath().getString("tokenType"))
                .isEqualTo(tokenType);
    }
}
