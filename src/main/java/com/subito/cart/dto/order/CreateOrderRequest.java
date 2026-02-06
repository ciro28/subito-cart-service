package com.subito.cart.dto.order;

import java.util.List;

public record CreateOrderRequest(
        List<OrderItemRequest> items
) {

    public void checkValidation() {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        items.forEach(OrderItemRequest::checkValidation);
    }
}
