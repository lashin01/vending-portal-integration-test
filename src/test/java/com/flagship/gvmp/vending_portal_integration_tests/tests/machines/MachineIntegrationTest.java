package com.flagship.gvmp.vending_portal_integration_tests.tests.machines;

import com.flagship.gvmp.vending_portal_integration_tests.base.BaseIntegrationTest;
import com.flagship.gvmp.vending_portal_integration_tests.clients.MachineClient;
import com.flagship.gvmp.vending_portal_integration_tests.config.TestProperties;
import com.flagship.gvmp.vending_portal_integration_tests.dto.machine.MachineDtos.MachineSummaryResponse;
import io.cucumber.messages.ndjson.internal.com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.messages.ndjson.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MachineIntegrationTest extends BaseIntegrationTest {

    private final MachineClient machineClient = new MachineClient();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MachineIntegrationTest(TestProperties testProperties) {
        super(testProperties);
    }

    @Test
    void shouldGetMachines() {

        Response response =
                machineClient.getMachines(
                        authenticate()
                );

        assertThat(response.statusCode())
                .isEqualTo(200);
    }

    @Test
    void shouldGetExistingMachine() throws Exception {

        Response machinesResponse =
                machineClient.getMachines(
                        authenticate()
                );

        assertThat(machinesResponse.statusCode())
                .isEqualTo(200);

        List<MachineSummaryResponse> machines =
                objectMapper.readValue(
                        machinesResponse.asString(),
                        new TypeReference<List<MachineSummaryResponse>>() {
                        }
                );

        assertThat(machines)
                .as("At least one machine should exist")
                .isNotEmpty();

        Long machineId =
                machines.get(0).id();

        Response response =
                machineClient.getMachine(
                        machineId,
                        authenticate()
                );

        assertThat(response.statusCode())
                .isEqualTo(200);

        assertThat(
                response.jsonPath().getLong("id")
        ).isEqualTo(machineId);
    }

    @Test
    void shouldGetExistingMachineTopology()
            throws Exception {

        Response machinesResponse =
                machineClient.getMachines(
                        authenticate()
                );

        assertThat(machinesResponse.statusCode())
                .isEqualTo(200);

        List<MachineSummaryResponse> machines =
                objectMapper.readValue(
                        machinesResponse.asString(),
                        new TypeReference<List<MachineSummaryResponse>>() {
                        }
                );

        assertThat(machines)
                .as("At least one machine should exist")
                .isNotEmpty();

        Long machineId =
                machines.get(0).id();

        Response response =
                machineClient.getTopology(
                        machineId,
                        authenticate()
                );

        assertThat(response.statusCode())
                .isEqualTo(200);

        assertThat(
                response.jsonPath().getLong("machineId")
        ).isEqualTo(machineId);
    }
}
