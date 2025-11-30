package com.mm.bookings.mapper;

import com.mm.bookings.dto.CreateOrderRequest;
import com.mm.bookings.response.OrderResponse;
import com.mm.bookings.dto.UpdateOrderPaymentRequest;
import com.mm.bookings.dto.UpdateOrderStatusRequest;
import com.mm.bookings.entity.Order;
import com.mm.bookings.enums.OrderStatus;
import com.mm.bookings.enums.PaymentStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    /**
     * Convert CreateOrderRequest → Order Entity
     */
    public Order toEntity(CreateOrderRequest request, String generatedOrderId) {

        return Order.builder()
                .orderId(generatedOrderId)
                .customerId(request.getCustomerId())
                .farmId(request.getFarmId())
                .amount(request.getAmount())
                .customerAddress(request.getCustomerAddress())
                .status(
                        request.getStatus() != null
                                ? OrderStatus.valueOf(request.getStatus())
                                : OrderStatus.PENDING
                )
                .paymentStatus(
                        request.getPaymentStatus() != null
                                ? PaymentStatus.valueOf(request.getPaymentStatus())
                                : PaymentStatus.PENDING
                )
                .build();
    }

    /**
     * Convert Order Entity → OrderResponse DTO
     */
    public OrderResponse toResponse(Order order) {

        if (order == null) return null;

        return OrderResponse.builder()
                .id(order.getId())
                .orderId(order.getOrderId())
                .customerId(order.getCustomerId())
                .farmId(order.getFarmId())
                .customerAddress(order.getCustomerAddress())
                .status(order.getStatus().name())
                .amount(order.getAmount())
                .paymentStatus(order.getPaymentStatus() != null
                        ? order.getPaymentStatus().name()
                        : null)
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .version(order.getVersion())
                .build();
    }

    /**
     * Update status field
     */
    public void updateStatus(Order order, UpdateOrderStatusRequest request) {
        order.setStatus(OrderStatus.valueOf(request.getStatus()));
    }

    /**
     * Update payment status field
     */
    public void updatePayment(Order order, UpdateOrderPaymentRequest request) {
        order.setPaymentStatus(PaymentStatus.valueOf(request.getPaymentStatus()));
    }
}
