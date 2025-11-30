package com.mm.bookings.client;


import com.mm.bookings.response.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "customer-service")
public interface CustomerClient {

    @GetMapping("/api/v1/customer/{id}")
    CustomerResponse getCustomerById(@PathVariable Long id);

    @GetMapping("/api/v1/customer/test")
    String testApi();
}
