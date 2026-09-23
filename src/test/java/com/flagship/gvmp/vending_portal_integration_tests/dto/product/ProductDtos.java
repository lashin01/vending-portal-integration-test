package com.flagship.gvmp.vending_portal_integration_tests.dto.product;

import java.math.BigDecimal;

public final class ProductDtos {

    private ProductDtos() {
    }

    public record CreateTmsProductRequest(
            String goldEraProductId
    ) {
    }

    public record TmsProductResponse(
            Long id,
            String goldEraProductId,
            String nameEn,
            String nameAr,
            String purity,
            String weight,
            BigDecimal weightValue,
            String weightUnit,
            String currency,
            String imageUrl,
            String brandEn,
            String brandAr,
            String categoryEn,
            String categoryAr,
            boolean active,
            BigDecimal liveSellPrice,
            String priceSyncedAt,
            String createdAt,
            String updatedAt
    ) {
    }

    public record EcommerceProductResponse(
            String goldEraProductId,
            String nameEn,
            String nameAr,
            String purity,
            String weight,
            BigDecimal weightValue,
            String weightUnit,
            String currency,
            String imageUrl,
            String brandEn,
            String brandAr,
            String categoryEn,
            String categoryAr,
            BigDecimal liveSellPrice,
            String priceSyncedAt
    ) {
    }
}
