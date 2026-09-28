package com.flagship.gvmp.vending_portal_integration_tests.tests.ports;

import com.flagship.gvmp.vending_portal_integration_tests.base.BaseIntegrationTest;
import com.flagship.gvmp.vending_portal_integration_tests.clients.MachineClient;
import com.flagship.gvmp.vending_portal_integration_tests.clients.PortClient;
import com.flagship.gvmp.vending_portal_integration_tests.dto.machine.MachineDtos.PortResponse;
import com.flagship.gvmp.vending_portal_integration_tests.dto.machine.MachineDtos.MachineSummaryResponse;
import com.flagship.gvmp.vending_portal_integration_tests.dto.machine.MachineDtos.TopologyResponse;
import com.flagship.gvmp.vending_portal_integration_tests.dto.port.PortDtos.UpdatePortRequest;
import io.cucumber.messages.ndjson.internal.com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.messages.ndjson.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PortIntegrationTest extends BaseIntegrationTest {

    private final MachineClient machineClient = new MachineClient();

    private final PortClient portClient = new PortClient();

    private final ObjectMapper objectMapper = new ObjectMapper();

    PortIntegrationTest(
            com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties testProperties
    ) {
        super(testProperties);
    }

    @Test
    void shouldUpdateExistingPort() throws Exception {

        Long portId = getExistingPortId();

        PortResponse existingPort = getExistingPort(portId);

        UpdatePortRequest request =
                new UpdatePortRequest(
                        existingPort.portCode(),
                        existingPort.hardwareAddress(),
                        existingPort.enabled()
                );

        Response response =
                portClient.updatePort(
                        portId,
                        request,
                        authenticate()
                );

        assertThat(response.statusCode())
                .isEqualTo(200);

        assertThat(
                response.jsonPath().getLong("id")
        ).isEqualTo(portId);
    }

    @Test
    void shouldDeletePort() throws Exception {

        /*
         * Do not delete an existing production/test port.
         *
         * This test should only be enabled once the test
         * environment has a dedicated way to create a
         * temporary port.
         */
        org.junit.jupiter.api.Assumptions.assumeTrue(
                false,
                "Delete port test requires a dedicated temporary test port."
        );
    }

    private Long getExistingMachineId()
            throws Exception {

        Response response = machineClient.getMachines(authenticate());

        assertThat(response.statusCode()).isEqualTo(200);

        List<MachineSummaryResponse> machines =
                objectMapper.readValue(
                        response.asString(),
                        new TypeReference<>() {
                        }
                );

        assertThat(machines)
                .as("At least one machine should exist")
                .isNotEmpty();

        return machines.get(0).id();
    }

    private Long getExistingPortId()
            throws Exception {

        Long machineId =
                getExistingMachineId();

        Response response =
                machineClient.getTopology(
                        machineId,
                        authenticate()
                );

        assertThat(response.statusCode())
                .isEqualTo(200);

        TopologyResponse topology =
                objectMapper.readValue(
                        response.asString(),
                        TopologyResponse.class
                );

        assertThat(topology.ports())
                .as("At least one port should exist")
                .isNotEmpty();

        return topology.ports()
                .get(0)
                .id();
    }

    private PortResponse getExistingPort(
            Long portId
    ) throws Exception {

        Long machineId =
                getExistingMachineId();

        Response response =
                machineClient.getTopology(
                        machineId,
                        authenticate()
                );

        assertThat(response.statusCode())
                .isEqualTo(200);

        TopologyResponse topology =
                objectMapper.readValue(
                        response.asString(),
                        TopologyResponse.class
                );

        return topology.ports()
                .stream()
                .filter(port -> port.id().equals(portId))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException(
                                "Port "
                                        + portId
                                        + " was not found in machine topology."
                        )
                );
    }
}
