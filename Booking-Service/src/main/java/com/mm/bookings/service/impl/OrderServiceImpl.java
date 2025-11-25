package com.mm.bookings.service.impl;

import com.mm.bookings.dto.CreateOrderRequest;
import com.mm.bookings.dto.OrderResponse;
import com.mm.bookings.dto.UpdateOrderPaymentRequest;
import com.mm.bookings.dto.UpdateOrderStatusRequest;
import com.mm.bookings.entity.Order;
import com.mm.bookings.enums.OrderStatus;
import com.mm.bookings.enums.PaymentStatus;
import com.mm.bookings.exception.OrderNotFoundException;
import com.mm.bookings.mapper.OrderMapper;
import com.mm.bookings.repository.OrderRepository;
import com.mm.bookings.service.OrderService;
import com.mm.bookings.utils.OrderIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderIdGenerator orderIdGenerator;

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {

        // 1. Generate unique orderId
        String orderId = orderIdGenerator.generate();

        // 2. Map DTO -> Entity
        Order order = orderMapper.toEntity(request, orderId);

        // 3. Set default values if needed
        if (order.getStatus() == null) {
            order.setStatus(OrderStatus.PENDING);
        }
        if (order.getPaymentStatus() == null) {
            order.setPaymentStatus(PaymentStatus.PENDING);
        }

        // 4. Persist
        order = orderRepository.save(order);

        // 5. Map back to response DTO
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse getByOrderId(String orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        return orderMapper.toResponse(order);
    }

    @Override
    public Page<OrderResponse> getOrders(Long customerId, Long farmId, String status, Pageable pageable) {

        OrderStatus statusEnum = null;
        if (status != null && !status.isBlank()) {
            statusEnum = OrderStatus.valueOf(status.toUpperCase(Locale.ROOT));
        }

        Page<Order> page = orderRepository.findAllByFilters(customerId, farmId, statusEnum, pageable);

        return page.map(orderMapper::toResponse);
    }

    @Override
    @Transactional
    public OrderResponse updateStatus(String orderId, UpdateOrderStatusRequest request) {

        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        // Business rules (example): cannot move from DELIVERED/CANCELLED
        if (order.getStatus() == OrderStatus.CANCELLED || order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot change status for completed/cancelled orders");
        }

        orderMapper.updateStatus(order, request);

        order = orderRepository.save(order);

        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse updatePayment(String orderId, UpdateOrderPaymentRequest request) {

        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        orderMapper.updatePayment(order, request);

        order = orderRepository.save(order);

        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(String orderId) {

        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        // Example rule: allow cancel only if PENDING or CONFIRMED
        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order cannot be cancelled in status: " + order.getStatus());
        }

        order.setStatus(OrderStatus.CANCELLED);
        order.setPaymentStatus(PaymentStatus.FAILED); // or keep as is based on your business logic

        order = orderRepository.save(order);

        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional
    public void deleteOrder(String orderId) {

        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        orderRepository.delete(order);
    }
}
