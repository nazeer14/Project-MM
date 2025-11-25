package com.mm.bookings.service;

import com.mm.bookings.dto.CreateOrderRequest;
import com.mm.bookings.dto.OrderResponse;
import com.mm.bookings.dto.UpdateOrderPaymentRequest;
import com.mm.bookings.dto.UpdateOrderStatusRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    OrderResponse getByOrderId(String orderId);

    Page<OrderResponse> getOrders(Long customerId, Long farmId, String status, Pageable pageable);

    OrderResponse updateStatus(String orderId, UpdateOrderStatusRequest request);

    OrderResponse updatePayment(String orderId, UpdateOrderPaymentRequest request);

    OrderResponse cancelOrder(String orderId);

    void deleteOrder(String orderId);
}
