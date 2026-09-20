package com.flagship.gvmp.vending_portal_integration_tests.dto.machine;

public final class BoxDtos {

    private BoxDtos() {
    }

    public record LinkProductRequest(
            Long tmsProductId,
            String goldEraProductId
    ) {
    }

    public record UpdateBoxLabelRequest(
            String label
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
