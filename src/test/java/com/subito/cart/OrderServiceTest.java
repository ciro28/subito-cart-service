package com.subito.cart;

import com.subito.cart.dto.order.CreateOrderRequest;
import com.subito.cart.dto.order.OrderResponse;
import com.subito.cart.dto.order.OrderItemRequest;
import com.subito.cart.entity.ProductEntity;
import com.subito.cart.exception.NotFoundException;
import com.subito.cart.repository.OrderRepository;
import com.subito.cart.repository.ProductRepository;
import com.subito.cart.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCreateOrderWithCorrectTotals() {
        ProductEntity product = new ProductEntity();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(new BigDecimal("100.00"));
        product.setVat(new BigDecimal("0.22"));

        when(productRepository.findByIdInAndDeletedIsFalse(List.of(1L))).thenReturn(List.of(product));
        when(orderRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        CreateOrderRequest request = new CreateOrderRequest(
                List.of(new OrderItemRequest(1L))
        );

        OrderResponse response = orderService.createOrder(request);

        assertEquals(new BigDecimal("100.00"), response.totalPrice());
        assertEquals(new BigDecimal("22.00"), response.totalVat());
        assertEquals(1, response.items().size());
    }

    @Test
    void shouldThrowIfProductNotFound() {
        when(productRepository.findByIdInAndDeletedIsFalse(List.of(99L))).thenReturn(List.of());

        CreateOrderRequest request = new CreateOrderRequest(
                List.of(new OrderItemRequest(99L))
        );

        assertThrows(NotFoundException.class,
                () -> orderService.createOrder(request));
    }
}
