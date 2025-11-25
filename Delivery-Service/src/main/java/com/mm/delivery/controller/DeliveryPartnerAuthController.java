package com.mm.delivery.controller;

import com.mm.delivery.dto.LoginResponse;
import com.mm.delivery.entity.DeliveryPartner;
import com.mm.delivery.service.DeliveryPartnerService;
import com.mm.delivery.utils.JwtUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class DeliveryPartnerAuthController {

    @Autowired
    private DeliveryPartnerService deliveryPartnerService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/save")
    public ResponseEntity<?> saveDeliveryPartner(@RequestBody DeliveryPartner deliveryPartner){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") @NotNull Long id){
        try {
            DeliveryPartner deliveryPartner = deliveryPartnerService.getByPartnerId(id);
            return ResponseEntity.ok(deliveryPartner);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try{
            DeliveryPartner deliveryPartner=deliveryPartnerService.verifyWithPassword(number,password);
            if(deliveryPartner.getId() != null){
                if(deliveryPartner.getLocked()){
                    return ResponseEntity.status(421).body("Your Locked Please Contact Customer Support");
                }
                String accessToken=jwtUtil.generateToken(deliveryPartner.getId(), number, List.of("AGENT","DP"));
                String refreshToken=jwtUtil.generateRefreshToken(number);
                LoginResponse response=new LoginResponse(accessToken,refreshToken,deliveryPartner);
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(401).body("Invalid Credentials");
        }catch(RuntimeException e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

}
