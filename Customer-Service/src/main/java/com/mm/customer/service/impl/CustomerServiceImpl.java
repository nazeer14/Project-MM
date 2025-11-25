package com.mm.customer.service.impl;

import com.mm.customer.client.OrdersClient;
import com.mm.customer.dto.OrderDto;
import com.mm.customer.entity.Customer;
import com.mm.customer.entity.LoginHistory;
import com.mm.customer.exception.CustomerNotFoundException;
import com.mm.customer.repository.CustomerRepository;
import com.mm.customer.repository.LoginHistoryRepository;
import com.mm.customer.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final OrdersClient ordersClient;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               LoginHistoryRepository loginHistoryRepository,
                               OrdersClient ordersClient) {
        this.customerRepository = customerRepository;
        this.loginHistoryRepository = loginHistoryRepository;
        this.ordersClient = ordersClient;
    }

    // -------------------------
    //        LOGIN
    // -------------------------
    @Override
    @Transactional
    public Customer loginCustomer(String number) {

        Customer customer = customerRepository.findByContactNumber(number)
                .map(c -> {
                    c.setUpdatedAt(LocalDateTime.now());
                    return c;
                })
                .orElseGet(() -> {
                    Customer c = new Customer();
                    c.setContactNumber(number);
                    c.setCreatedAt(LocalDateTime.now());
                    c.setUpdatedAt(LocalDateTime.now());
                    c.setVerified(true);
                    return c;
                });

        customer.setIsLogin(true);
        Customer savedCustomer = customerRepository.save(customer);

        LoginHistory history = new LoginHistory();
        history.setCustomer(savedCustomer);
        history.setIsLogin(true);
        history.setLoginTimestamp(LocalDateTime.now());
        loginHistoryRepository.save(history);

        return savedCustomer;
    }

    // -------------------------
    //     GET BY ID
    // -------------------------
    @Override
    public Customer getById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    // -------------------------
    //      GET ORDERS
    // -------------------------
    @Override
    public List<OrderDto> findOrdersByCustomerId(Long id) {
        return customerRepository.existsById(id)
                ? ordersClient.getCustomerOrders(id)
                : List.of();
    }

    // -------------------------
    //        LOGOUT
    // -------------------------
    @Override
    @Transactional
    public void logoutCustomer(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        customer.setIsLogin(false);
        customerRepository.save(customer);

        loginHistoryRepository.findFirstByCustomerIdAndIsLoginTrueOrderByLoginTimestampDesc(id)
                .ifPresentOrElse(login -> {
                    login.setLogoutTimestamp(LocalDateTime.now());
                    login.setIsLogin(false);
                    loginHistoryRepository.save(login);
                }, () -> log.warn("Customer {} logout: No active login history found", id));
    }
}
