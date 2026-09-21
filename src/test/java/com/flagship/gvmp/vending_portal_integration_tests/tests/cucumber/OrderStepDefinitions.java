package com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber;

import com.flagship.gvmp.vending_portal_integration_tests.clients.OrderClient;
import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import com.flagship.gvmp.vending_portal_integration_tests.auth.AccessTokenProvider;
import com.flagship.gvmp.vending_portal_integration_tests.clients.AuthClient;
import com.flagship.gvmp.vending_portal_integration_tests.dto.order.OrderDtos.CreateOrderRequest;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.configureRestAssured;
import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.requireBackendReachable;
import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.requireId;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderStepDefinitions {

    private final TestProperties testProperties;
    private final CucumberScenarioContext context;
    private final AccessTokenProvider accessTokenProvider;

    @Autowired
    public OrderStepDefinitions(TestProperties testProperties, CucumberScenarioContext context) {
        this.testProperties = testProperties;
        this.context = context;
        this.accessTokenProvider = new AccessTokenProvider(new AuthClient(), testProperties);
    }

    @Before
    public void configureOrderRestAssured() {
        configureRestAssured(testProperties);
    }

    @Given("the order API is available")
    public void theOrderApiIsAvailable() {
        requireBackendReachable(testProperties);
    }

    @Given("an order admin access token is available")
    public void anOrderAdminAccessTokenIsAvailable() {
        context.setAccessToken(accessTokenProvider.getAccessToken());
    }

    @Given("an existing machine id is configured")
    public void anExistingMachineIdIsConfigured() {
        context.setCurrentMachineId(requireId(testProperties.getMachineId(), "Set TMS_TEST_MACHINE_ID to an existing backend machine id."));
    }

    @Given("an existing order id is configured")
    public void anExistingOrderIdIsConfigured() {
        context.setCurrentOrderId(requireId(testProperties.getOrderId(), "Set TMS_TEST_ORDER_ID to an existing backend order id."));
    }

    @Given("an existing order document id is configured")
    public void anExistingOrderDocumentIdIsConfigured() {
        context.setCurrentDocumentId(requireId(testProperties.getOrderDocumentId(), "Set TMS_TEST_ORDER_DOCUMENT_ID to an existing backend order document id."));
    }

    @Given("valid order creation data is configured")
    public void validOrderCreationDataIsConfigured() {
        context.setCurrentMachineId(requireId(testProperties.getMachineId(), "Set TMS_TEST_MACHINE_ID to create an order."));
    }

    @Given("an existing payable order id is configured")
    public void anExistingPayableOrderIdIsConfigured() {
        context.setCurrentOrderId(requireId(testProperties.getMarkPaidOrderId(), "Set TMS_TEST_MARK_PAID_ORDER_ID to run this scenario."));
    }

    @Given("an existing dispensable order id is configured")
    public void anExistingDispensableOrderIdIsConfigured() {
        context.setCurrentOrderId(requireId(testProperties.getDispenseOrderId(), "Set TMS_TEST_DISPENSE_ORDER_ID to run this scenario."));
    }

    @Given("an existing completable order id is configured")
    public void anExistingCompletableOrderIdIsConfigured() {
        context.setCurrentOrderId(requireId(testProperties.getCompleteOrderId(), "Set TMS_TEST_COMPLETE_ORDER_ID to run this scenario."));
    }

    @Given("an existing failable order id is configured")
    public void anExistingFailableOrderIdIsConfigured() {
        context.setCurrentOrderId(requireId(testProperties.getFailOrderId(), "Set TMS_TEST_FAIL_ORDER_ID to run this scenario."));
    }

    @Given("an existing cancelable order id is configured")
    public void anExistingCancelableOrderIdIsConfigured() {
        context.setCurrentOrderId(requireId(testProperties.getCancelOrderId(), "Set TMS_TEST_CANCEL_ORDER_ID to run this scenario."));
    }

    @When("I list orders")
    public void iListOrders() {
        context.setResponse(orderClient().listOrders());
    }

    @When("I list orders for the configured machine")
    public void iListOrdersForTheConfiguredMachine() {
        context.setResponse(orderClient().listOrders(context.getCurrentMachineId()));
    }

    @When("I get the configured order")
    public void iGetTheConfiguredOrder() {
        context.setResponse(orderClient().getOrder(context.getCurrentOrderId()));
    }

    @When("I list documents for the configured order")
    public void iListDocumentsForTheConfiguredOrder() {
        context.setResponse(orderClient().listDocuments(context.getCurrentOrderId()));
    }

    @When("I get the configured order document")
    public void iGetTheConfiguredOrderDocument() {
        context.setResponse(orderClient().getDocument(context.getCurrentOrderId(), context.getCurrentDocumentId()));
    }

    @When("I create an order")
    public void iCreateAnOrder() {
        context.setResponse(orderClient().create(new CreateOrderRequest(
                testProperties.getMachineId(),
                testProperties.getTmsProductId(),
                testProperties.getGoldEraProductId()
        )));
    }

    @When("I mark the configured order paid")
    public void iMarkTheConfiguredOrderPaid() {
        context.setResponse(orderClient().markPaid(context.getCurrentOrderId()));
    }

    @When("I dispense the configured order")
    public void iDispenseTheConfiguredOrder() {
        context.setResponse(orderClient().dispense(context.getCurrentOrderId()));
    }

    @When("I complete the configured order")
    public void iCompleteTheConfiguredOrder() {
        context.setResponse(orderClient().complete(context.getCurrentOrderId()));
    }

    @When("I fail the configured order")
    public void iFailTheConfiguredOrder() {
        context.setResponse(orderClient().fail(context.getCurrentOrderId()));
    }

    @When("I cancel the configured order")
    public void iCancelTheConfiguredOrder() {
        context.setResponse(orderClient().cancel(context.getCurrentOrderId()));
    }

    @Then("the order response status should be {int}")
    public void theOrderResponseStatusShouldBe(int expectedStatusCode) {
        assertThat(context.getResponse().statusCode())
                .isEqualTo(expectedStatusCode);
    }

    @Then("the response body should be an order collection")
    @Then("the response body should be an order document collection")
    public void theResponseBodyShouldBeACollection() {
        assertThat(context.getResponse().jsonPath().getList("$"))
                .isNotNull();
    }

    @Then("the response should describe the configured order")
    public void theResponseShouldDescribeTheConfiguredOrder() {
        assertThat(context.getResponse().jsonPath().getLong("id"))
                .isEqualTo(context.getCurrentOrderId());
    }

    @Then("the response should describe the created order")
    public void theResponseShouldDescribeTheCreatedOrder() {
        assertThat(context.getResponse().jsonPath().getLong("id"))
                .isNotNull();
    }

    @Then("the response body should contain document bytes")
    public void theResponseBodyShouldContainDocumentBytes() {
        assertThat(context.getResponse().getBody().asByteArray())
                .isNotEmpty();
    }

    private OrderClient orderClient() {
        return new OrderClient(context.getAccessToken());
    }
}
