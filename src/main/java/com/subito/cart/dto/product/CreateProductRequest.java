package com.subito.cart.dto.product;

import java.math.BigDecimal;

public record CreateProductRequest(
        String name,
        BigDecimal price,
        BigDecimal vat
        ) {

        public void checkValidation() {
               if(name == null || name.isEmpty()) {
                       throw new IllegalArgumentException("Product name cannot be empty");
               }

               if(price == null || price.compareTo(BigDecimal.ZERO) < 0) {
                       throw new IllegalArgumentException("Product price cannot be negative");
               }

               if(vat == null || vat.compareTo(BigDecimal.ZERO) < 0) {
                       throw new IllegalArgumentException("Product vat cannot be negative");
               }
        }
}
