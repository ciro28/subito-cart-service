package com.subito.cart.repository;

import java.math.BigDecimal;

public interface OrderProductProjection {
    Long getOrderId();
    BigDecimal getTotalPrice();
    BigDecimal getTotalVat();
    Long getProductId();
    BigDecimal getPrice();
    BigDecimal getVat();
}

