package com.mm.admin.controller;

import com.mm.admin.dto.SuperAdminRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/superadmin")
public class SuperAdminController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SuperAdminRequest request){

        return ResponseEntity.ok("Data N/A " + request.getNumber());
    }
}
