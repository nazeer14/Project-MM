package com.mm.customer.client;

import com.mm.customer.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "customer-service")
public interface OrdersClient {

    @GetMapping("/api/order/customer/{id}")
    List<OrderDto> getCustomerOrders(@PathVariable Long id);


}
