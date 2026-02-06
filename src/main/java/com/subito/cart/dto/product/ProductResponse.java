package com.subito.cart.dto.product;

import java.math.BigDecimal;

public record ProductResponse(
        Long productId,
        String name,
        BigDecimal price,
        BigDecimal vat
) {
}
