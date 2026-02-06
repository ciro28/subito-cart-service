package com.subito.cart.dto.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        Long orderId,
        BigDecimal totalPrice,
        BigDecimal totalVat,
        List<OrderItemResponse> items
) {}
