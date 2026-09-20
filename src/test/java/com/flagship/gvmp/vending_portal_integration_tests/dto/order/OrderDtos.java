package com.flagship.gvmp.vending_portal_integration_tests.dto.order;

import java.math.BigDecimal;
import java.util.List;

public final class OrderDtos {

    private OrderDtos() {
    }

    public record CreateOrderRequest(
            Long machineId,
            Long tmsProductId,
            String goldEraProductId
    ) {
    }

    public record OrderItemResponse(
            String barcode,
            String portHardwareId,
            String boxHardwareId,
            Integer itemIndex
    ) {
    }

    public record OrderResponse(
            Long id,
            Long machineId,
            String machineCode,
            Long boxId,
            String boxSide,
            Long portId,
            int portNumber,
            Long tmsProductId,
            String goldEraProductId,
            BigDecimal unitPrice,
            String currency,
            String status,
            String paymentRef,
            String dispenseResult,
            String createdAt,
            String updatedAt,
            String paidAt,
            String completedAt,
            String language,
            String phoneNumber,
            String kycVerificationId,
            boolean refundRequested,
            String ecrTransactionId,
            String reservedBarcode,
            String barcode,
            String portHardwareId,
            String boxHardwareId,
            String productReference,
            BigDecimal lockedPrice,
            String paymentId,
            String customerId,
            List<OrderItemResponse> items
    ) {
    }

    public record OrderDocumentMetaResponse(
            Long id,
            Long orderId,
            String documentType,
            String contentType,
            String filename,
            int sizeBytes,
            String createdAt
    ) {
    }
}
