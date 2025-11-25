package com.mm.customer.service;

import com.mm.customer.client.OrdersClient;
import com.mm.customer.entity.Customer;
import com.mm.customer.entity.LoginHistory;
import com.mm.customer.repository.CustomerRepository;
import com.mm.customer.repository.LoginHistoryRepository;
import com.mm.customer.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    private final CustomerRepository customerRepo = mock(CustomerRepository.class);
    private final LoginHistoryRepository loginRepo = mock(LoginHistoryRepository.class);
    private final OrdersClient ordersClient = mock(OrdersClient.class);

    private final CustomerServiceImpl service =
            new CustomerServiceImpl(customerRepo, loginRepo, ordersClient);

    @Test
    void testLoginNewCustomer() {
        String mobile = "9999999999";
        Customer saved = new Customer();
        saved.setId(1L);
        saved.setContactNumber(mobile);

        when(customerRepo.findByContactNumber(mobile)).thenReturn(Optional.empty());
        when(customerRepo.save(any())).thenReturn(saved);

        Customer result = service.loginCustomer(mobile);

        assertEquals(mobile, result.getContactNumber());
        verify(loginRepo, times(1)).save(any(LoginHistory.class));
    }

    @Test
    void testLogoutCustomer() {
        Long id = 1L;
        Customer customer = new Customer();
        customer.setId(id);

        LoginHistory history = new LoginHistory();
        history.setId(10L);

        when(customerRepo.findById(id)).thenReturn(Optional.of(customer));
        when(loginRepo.findFirstByCustomerIdAndIsLoginTrueOrderByLoginTimestampDesc(id))
                .thenReturn(Optional.of(history));

        service.logoutCustomer(id);

        verify(customerRepo, times(1)).save(customer);
        verify(loginRepo, times(1)).save(history);
    }
}
