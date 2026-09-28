package com.flagship.gvmp.vending_portal_integration_tests.dto.port;

public final class PortDtos {

    private PortDtos() {
    }

    public record UpdatePortRequest(
            String portCode,
            String hardwareAddress,
            Boolean enabled
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
