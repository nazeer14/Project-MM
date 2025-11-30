package com.mm.customer.controller;

import com.mm.customer.dto.CustomerResponse;
import com.mm.customer.dto.OrderDto;
import com.mm.customer.entity.Customer;
import com.mm.customer.response.ApiResponse;
import com.mm.customer.service.CustomerService;
import jakarta.ws.rs.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/test")
    public ResponseEntity<String> testApi(){
        return ResponseEntity.ok("Test API called successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByCustomerId(@PathVariable("id") Long id){
        Customer customer=customerService.getById(id);
        CustomerResponse customerResponse=
                new CustomerResponse(
                        customer.getId(),
                        customer.getName(),
                        customer.getLocation(),
                        customer.getContactNumber(),
                        customer.getVerified(),
                        customer.getLocked()
                        );

        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping("/get-orders/{id}")
    public ResponseEntity<?> getAllCustomerOrders(@PathVariable Long id){
        try {
            List<OrderDto> orders = customerService.findOrdersByCustomerId(id);
            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer){
        try{
            return ResponseEntity.ok("Successfully updated");
        }catch (RuntimeException e){
            return ResponseEntity.status(505).body(e.getMessage());
        }
    }

    @PostMapping("/logout/{id}")
    public ApiResponse logoutCustomer(@PathVariable("id") Long id){
        try{
            customerService.logoutCustomer(id);
            return ApiResponse.success("Logout Successfully");
        } catch (RuntimeException e) {
            return ApiResponse.error(505,e.getMessage());
        }
    }
}
