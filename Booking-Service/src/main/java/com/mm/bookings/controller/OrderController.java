package com.mm.bookings.controller;

import com.mm.bookings.client.CustomerClient;
import com.mm.bookings.dto.*;
import com.mm.bookings.response.ApiResponse;
import com.mm.bookings.response.OrderResponse;
import com.mm.bookings.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerClient customerClient;

    @GetMapping("/feignTest")
    public ResponseEntity<ApiResponse<?>> testFeignClient(){

        return ResponseEntity.ok(ApiResponse.ok("Successfully called api",customerClient.testApi()));
    }

    /**
     * Create a new order
     */
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderResponse createdOrder = orderService.createOrder(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{orderId}")
                .buildAndExpand(createdOrder.getOrderId())
                .toUri();

        ApiResponse<OrderResponse> response=ApiResponse.ok("Order Created Successfully",createdOrder);
        return ResponseEntity.created(location).body(response);
    }

    /**
     * Get single order by business orderId (eg: ORD000123)
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderByOrderId(@PathVariable String orderId) {
        OrderResponse order = orderService.getByOrderId(orderId);
        return ResponseEntity.ok(order);
    }

    /**
     * Get all orders with optional filters + pagination
     * Example: /api/v1/orders?customerId=1&status=PENDING&page=0&size=20
     */
    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getOrders(
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) Long farmId,
            @RequestParam(required = false) String status,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<OrderResponse> orders = orderService.getOrders(customerId, farmId, status, pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * Get all orders for a customer (short-hand API for mobile usage)
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Page<OrderResponse>> getOrdersByCustomer(
            @PathVariable Long customerId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<OrderResponse> orders = orderService.getOrders(customerId, null, null, pageable);
        return ResponseEntity.ok(orders);
    }

    /**
     * Update order status (PENDING → CONFIRMED → DELIVERED / CANCELLED etc.)
     */
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable String orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request
    ) {
        OrderResponse updated = orderService.updateStatus(orderId, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Update payment info: mode + status
     */
    @PatchMapping("/{orderId}/payment")
    public ResponseEntity<OrderResponse> updateOrderPayment(
            @PathVariable String orderId,
            @Valid @RequestBody UpdateOrderPaymentRequest request
    ) {
        OrderResponse updated = orderService.updatePayment(orderId, request);
        return ResponseEntity.ok(updated);
    }

    /**
     * Cancel an order (business rule: allowed only in some statuses)
     */
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable String orderId) {
        OrderResponse cancelled = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(cancelled);
    }

    /**
     * Hard delete (usually only for admin / testing).
     * In real systems you may want soft delete instead.
     */
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable String orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
