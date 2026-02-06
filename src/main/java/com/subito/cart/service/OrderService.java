package com.subito.cart.service;

import com.subito.cart.dto.order.CreateOrderRequest;
import com.subito.cart.dto.order.OrderResponse;
import com.subito.cart.dto.order.OrderItemRequest;
import com.subito.cart.dto.order.OrderItemResponse;
import com.subito.cart.entity.OrderEntity;
import com.subito.cart.entity.OrderProductEntity;
import com.subito.cart.entity.ProductEntity;
import com.subito.cart.exception.NotFoundException;
import com.subito.cart.repository.OrderProductProjection;
import com.subito.cart.repository.OrderRepository;
import com.subito.cart.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(CreateOrderRequest request) {
        OrderEntity order = new OrderEntity();

        List<OrderItemResponse> responseItems = new ArrayList<>();

        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalVat = BigDecimal.ZERO;

        List<Long> productIds = request.items().stream()
                .map(OrderItemRequest::productId)
                .toList();

        List<ProductEntity> products = productRepository.findByIdInAndDeletedIsFalse(productIds);
        if (products.size() != productIds.size() ) {
            throw new NotFoundException("One o more Products not found");
        }
        for (OrderItemRequest itemReq : request.items()) {

            ProductEntity product = products.stream()
                    .filter(p -> p.getId().equals(itemReq.productId()))
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalArgumentException("Product not found: " + itemReq.productId())
                    );

            OrderProductEntity orderProduct = new OrderProductEntity();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setPrice(product.getPrice());
            orderProduct.setVat(product.getVat());

            order.getItems().add(orderProduct);

            BigDecimal price = product.getPrice()
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal vat = price
                    .multiply(product.getVat())
                    .setScale(2, RoundingMode.HALF_UP);

            totalPrice = totalPrice.add(price);
            totalVat = totalVat.add(vat);

            responseItems.add(new OrderItemResponse(
                    product.getId(),
                    price,
                    vat
            ));
        }

        order.setTotalPrice(totalPrice);
        order.setTotalVat(totalVat);
        OrderEntity savedOrder = orderRepository.save(order);

        return new OrderResponse(
                savedOrder.getId(),
                totalPrice.setScale(2, RoundingMode.HALF_UP),
                totalVat.setScale(2, RoundingMode.HALF_UP),
                responseItems
        );
    }

    public List<OrderResponse> getOrders() {
        List<OrderProductProjection> list = orderRepository.findAllOrderProducts();

        Map<Long, List<OrderProductProjection>> grouped = list.stream()
                .collect(Collectors.groupingBy(OrderProductProjection::getOrderId));

       return grouped.entrySet().stream()
                .map(entry -> {
                    Long orderId = entry.getKey();
                    List<OrderProductProjection> products = entry.getValue();
                    BigDecimal totalPrice = products.get(0).getTotalPrice();
                    BigDecimal totalVat = products.get(0).getTotalVat();

                    List<OrderItemResponse> items = products.stream()
                            .map(p -> new OrderItemResponse(p.getProductId(), p.getPrice(), p.getVat()))
                            .toList();

                    return new OrderResponse(orderId, totalPrice, totalVat, items);
                })
                .toList();
    }

    public void deleteOrder(Long orderId) {
        OrderEntity productEntity = checkOrder(orderId);
        productEntity.setDeleted(true);
        orderRepository.save(productEntity);
    }

    private OrderEntity checkOrder(Long orderId) {
        if(orderId == null) {
            throw new IllegalArgumentException("Order Id is null");
        }
        return orderRepository.findByIdAndDeletedIsFalse(orderId).orElseThrow(() -> new NotFoundException("Order not found"));
    }
}
