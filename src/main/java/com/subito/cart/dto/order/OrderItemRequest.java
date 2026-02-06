package com.subito.cart.dto.order;

public record OrderItemRequest(
        Long productId
) {

    public void checkValidation() {
        if(productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID cannot be negative or 0");
        }
    }
}
