package com.mm.delivery.controller;

import com.mm.delivery.service.DeliveryPartnerService;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/partner")
public class DeliveryPartnerController {

    @Autowired
    private DeliveryPartnerService deliveryPartnerService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getDeliveryPartner(@PathVariable Long id){
        try{
            return ResponseEntity.ok(deliveryPartnerService.getByPartnerId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

}
