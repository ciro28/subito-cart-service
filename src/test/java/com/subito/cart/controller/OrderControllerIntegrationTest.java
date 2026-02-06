package com.subito.cart.controller;

import com.subito.cart.entity.ProductEntity;
import com.subito.cart.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {

        ProductEntity product = new ProductEntity();
        product.setName("Product 1");
        product.setPrice(new BigDecimal("10.00"));
        product.setVat(new BigDecimal("0.22"));

        product = productRepository.save(product);

        String body = """
            {
              "items": [
                { "productId": %d }
              ]
            }
            """.formatted(product.getId());

        mockMvc.perform(post("/api/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalPrice").value(10.00))
                .andExpect(jsonPath("$.totalVat").value(2.2));
    }
}

