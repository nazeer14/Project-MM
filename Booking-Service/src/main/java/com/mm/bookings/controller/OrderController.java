package com.mm.bookings.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order/")
public class OrderController {

    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getOrdersByCustomerId(@PathVariable Long id){
        try{
            return ResponseEntity.ok(List.of());

        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
