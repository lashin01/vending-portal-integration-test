package com.flagship.gvmp.vending_portal_integration_tests.tests.cucumber;

import com.flagship.gvmp.vending_portal_integration_tests.clients.MachineClient;
import com.flagship.gvmp.vending_portal_integration_tests.clients.PortClient;
import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import com.flagship.gvmp.vending_portal_integration_tests.dto.machine.MachineDtos.PortResponse;
import com.flagship.gvmp.vending_portal_integration_tests.dto.machine.MachineDtos.MachineSummaryResponse;
import com.flagship.gvmp.vending_portal_integration_tests.dto.machine.MachineDtos.TopologyResponse;
import com.flagship.gvmp.vending_portal_integration_tests.dto.port.PortDtos;
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

public class PortStepDefinitions {

    private final TestProperties testProperties;
    private final CucumberScenarioContext context;

    private final MachineClient machineClient = new MachineClient();

    private final PortClient portClient = new PortClient();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private PortResponse currentPort;

    @Autowired
    public PortStepDefinitions(TestProperties testProperties, CucumberScenarioContext context) {
        this.testProperties = testProperties;
        this.context = context;
    }

    @Before
    public void configurePortRestAssured() {
        configureRestAssured(testProperties);
    }

    @Given("the port API is available")
    public void thePortApiIsAvailable() {
        requireBackendReachable(testProperties);
    }

    @Given("an existing port is available")
    public void anExistingPortIsAvailable()
            throws Exception {

        ResponseData responseData = findExistingPort();

        context.setCurrentPortId(responseData.port().id());

        currentPort = responseData.port();
    }

    @When("I update that port with its current values")
    public void iUpdateThatPortWithItsCurrentValues() {

        Long portId = requireId(context.getCurrentPortId(),"No existing port is available.");

        assertThat(currentPort).as("Current port should be available.").isNotNull();

        PortDtos.UpdatePortRequest request =
                new PortDtos.UpdatePortRequest(
                        currentPort.portCode(),
                        currentPort.hardwareAddress(),
                        currentPort.enabled()
                );

        context.setResponse(portClient.updatePort(portId, request, context.getAccessToken()));
    }

    @Then("the port response status should be {int}")
    public void thePortResponseStatusShouldBe(int expectedStatusCode) {

        assertThat(context.getResponse()).isNotNull();

        assertThat(context.getResponse().statusCode()).isEqualTo(expectedStatusCode);
    }

    @Then("the response should describe the requested port")
    public void theResponseShouldDescribeTheRequestedPort() {

        Long portId = requireId(context.getCurrentPortId(),"No existing port is available.");

        assertThat(context.getResponse().jsonPath().getLong("id")).isEqualTo(portId);
    }

    private ResponseData findExistingPort()
            throws Exception {

        var machinesResponse = machineClient.getMachines(context.getAccessToken());

        assertThat(machinesResponse.statusCode()).isEqualTo(200);

        List<MachineSummaryResponse> machines =
                objectMapper.readValue(
                        machinesResponse.asString(),
                        new TypeReference<>() {
                        }
                );

        assertThat(machines).as("At least one machine should exist.").isNotEmpty();

        Long machineId = machines.get(1).id();

        System.out.println("Selected machine ID: " + machineId);
        System.out.println("Machines response: " + machinesResponse.asString());

        var topologyResponse = machineClient.getTopology(machineId, context.getAccessToken());

        System.out.println("Topology status: " + topologyResponse.statusCode());
        System.out.println("Topology response: " + topologyResponse.asString());

        assertThat(topologyResponse.statusCode()).isEqualTo(200);

        TopologyResponse topology = objectMapper.readValue(topologyResponse.asString(), TopologyResponse.class);

        assertThat(topology.ports()).as("At least one port should exist.").isNotEmpty();

        PortResponse port = topology.ports().get(0);

        return new ResponseData(machineId, port);
    }

    private record ResponseData(Long machineId, PortResponse port) {
    }
}
