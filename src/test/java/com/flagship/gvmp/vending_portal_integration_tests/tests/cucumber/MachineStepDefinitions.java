package com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber;

import com.flagship.gvmp.vending_portal_integration_tests.clients.MachineClient;
import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import com.flagship.gvmp.vending_portal_integration_tests.dto.machine.MachineDtos.MachineSummaryResponse;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.ndjson.internal.com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.messages.ndjson.internal.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.configureRestAssured;
import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.requireBackendReachable;
import static com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber.CucumberStepSupport.requireId;
import static org.assertj.core.api.Assertions.assertThat;

public class MachineStepDefinitions {

    private final TestProperties testProperties;
    private final CucumberScenarioContext context;

    private final MachineClient machineClient =
            new MachineClient();

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @Autowired
    public MachineStepDefinitions(
            TestProperties testProperties,
            CucumberScenarioContext context
    ) {
        this.testProperties = testProperties;
        this.context = context;
    }

    @Before
    public void configureMachineRestAssured() {
        configureRestAssured(testProperties);
    }

    @Given("the machine API is available")
    public void theMachineApiIsAvailable() {
        requireBackendReachable(testProperties);
    }

    @When("I request the list of machines")
    public void iRequestTheListOfMachines() {

        context.setResponse(
                machineClient.getMachines(
                        getAccessToken()
                )
        );
    }

    @Given("an existing machine is available")
    public void anExistingMachineIsAvailable()
            throws Exception {

        context.setResponse(
                machineClient.getMachines(
                        getAccessToken()
                )
        );

        assertThat(context.getResponse().statusCode())
                .isEqualTo(200);

        List<MachineSummaryResponse> machines =
                objectMapper.readValue(
                        context.getResponse().asString(),
                        new TypeReference<>() {
                        }
                );

        if (machines.isEmpty()) {
            throw new AssertionError(
                    "No machines exist in the test environment."
            );
        }

        Long machineId =
                machines.get(0).id();

        context.setCurrentMachineId(machineId);
    }

    @When("I request that machine")
    public void iRequestThatMachine() {

        Long machineId =
                requireId(
                        context.getCurrentMachineId(),
                        "No existing machine is available."
                );

        context.setResponse(
                machineClient.getMachine(
                        machineId,
                        getAccessToken()
                )
        );
    }

    @When("I request the topology of that machine")
    public void iRequestTheTopologyOfThatMachine() {

        Long machineId =
                requireId(
                        context.getCurrentMachineId(),
                        "No existing machine is available."
                );

        context.setResponse(
                machineClient.getTopology(
                        machineId,
                        getAccessToken()
                )
        );
    }

    @Then("the machine response status should be {int}")
    public void theMachineResponseStatusShouldBe(
            int expectedStatusCode
    ) {

        assertThat(context.getResponse())
                .isNotNull();

        assertThat(context.getResponse().statusCode())
                .isEqualTo(expectedStatusCode);
    }

    @Then("the response should describe the requested machine")
    public void theResponseShouldDescribeTheRequestedMachine() {

        Long machineId =
                requireId(
                        context.getCurrentMachineId(),
                        "No existing machine is available."
                );

        assertThat(
                context.getResponse()
                        .jsonPath()
                        .getLong("id")
        ).isEqualTo(machineId);
    }

    @Then("the machine topology response status should be {int}")
    public void theMachineTopologyResponseStatusShouldBe(
            int expectedStatusCode
    ) {

        assertThat(context.getResponse())
                .isNotNull();

        assertThat(context.getResponse().statusCode())
                .isEqualTo(expectedStatusCode);
    }

    private String getAccessToken() {

        /*
         * Use the access-token already stored
         * in CucumberScenarioContext by AuthStepDefinitions.
         */
        return context.getAccessToken();
    }
}
