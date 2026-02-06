package com.subito.cart.controller;

import com.subito.cart.dto.order.CreateOrderRequest;
import com.subito.cart.dto.order.OrderResponse;
import com.subito.cart.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody CreateOrderRequest request) {
        checkValidationProduct(request);
        return orderService.createOrder(request);
    }

    @GetMapping("/list")
    public List<OrderResponse> getOrders() {
        return orderService.getOrders();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    private void checkValidationProduct(CreateOrderRequest request) {
        request.checkValidation();
    }
}


