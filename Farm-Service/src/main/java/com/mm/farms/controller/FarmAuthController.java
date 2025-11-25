package com.mm.farms.controller;

import com.mm.farms.dto.LoginResponse;
import com.mm.farms.entity.Farm;
import com.mm.farms.repository.FarmRepository;
import com.mm.farms.service.FarmAuthService;
import com.mm.farms.utils.JwtUtil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class FarmAuthController {

    @Autowired
    private FarmAuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private FarmRepository farmRepository;

    @GetMapping("/mail_send")
    public ResponseEntity<?> sendCode(@RequestParam @Email(message = "Invalid email") String email) {
        authService.sendCode(email);
        return ResponseEntity.ok("code sent to your email");
    }

    @PostMapping("/mail_verify")
    public ResponseEntity<?> verifyCode(@RequestParam String email, @RequestParam String code) {
        boolean isValid = authService.verifyCode(email, code);
        if (isValid) {
            return ResponseEntity.ok("Code verified successfully");
        }
        return ResponseEntity.badRequest().body("Invalid or expired Code");
    }
    @GetMapping("/send")
    public ResponseEntity<?> sendCodeToNumber(@RequestParam @Pattern(regexp = "^[0-9]{10,15}$", message = "Invalid contact number") String number) {
        authService.sendOtp(number);
        return ResponseEntity.ok("OTP code sent to your Number");
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestParam @Pattern(regexp = "^[0-9]{10,15}$", message = "Invalid contact number") String number , @RequestParam String otp) {
        boolean isValid = authService.verifyOtp(number, otp);
        if (isValid) {
            return ResponseEntity.ok("OTP verified successfully");
        }
        return ResponseEntity.badRequest().body("Invalid OTP");
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String number,@RequestParam String password) {
        try {
            Farm farm = authService.farmLoginUsingPassword(number, password);
            String accessToken = jwtUtil.generateToken(farm.getId(), number, List.of("FARM", "USER"));
            String refreshToken = jwtUtil.generateRefreshToken(number);
            return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken, LocalDateTime.now()));
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
