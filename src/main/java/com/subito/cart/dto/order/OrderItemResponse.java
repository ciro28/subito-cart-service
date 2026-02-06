package com.subito.cart.dto.order;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long productId,
        BigDecimal price,
        BigDecimal vat
) {}
