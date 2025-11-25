package com.mm.customer.service;

import com.mm.customer.dto.OrderDto;
import com.mm.customer.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    Customer loginCustomer(String number);

    Customer getById(Long id);

    List<OrderDto> findOrdersByCustomerId(Long id);

    void logoutCustomer(Long id);
}
