package com.flagship.gvmp.vending_portal_integration_tests.dto.machine;

import java.util.List;

public final class MachineDtos {

    private MachineDtos() {
    }

    public record MachineSummaryResponse(
            Long id,
            String code,
            String name,
            String location,
            String merchantCode,
            String operationalStatus,
            String connectivityStatus,
            String lastSeenAt,
            String firmwareVersion,
            boolean secretSet,
            String authSecretRotatedAt,
            Object boxCounts,
            String cameraPreviewUrl,
            Object camera,
            String createdAt,
            String updatedAt
    ) {
    }

    public record MachineDetailResponse(
            Long id,
            String code,
            String name,
            String location,
            String merchantCode,
            String operationalStatus,
            String connectivityStatus,
            String lastSeenAt,
            String firmwareVersion,
            boolean secretSet,
            String authSecretRotatedAt,
            String cameraPreviewUrl,
            Object camera,
            String createdAt,
            String updatedAt
    ) {
    }

    public record TopologyResponse(
            Long machineId,
            String machineCode,
            List<PortResponse> ports
    ) {
    }

    public record PortResponse(
            Long id,
            int portNumber,
            String portCode,
            String hardwareAddress,
            boolean enabled,
            List<BoxResponse> boxes
    ) {
    }

    public record BoxResponse(
            Long id,
            String side,
            String occupancy,
            String label,
            Long tmsProductId,
            String goldEraProductId,
            String productNameEn,
            String productNameAr,
            String productPurity,
            String productWeight,
            String productCurrency,
            String linkedAt,
            int quantity,
            Integer capacity
    ) {
    }
}
