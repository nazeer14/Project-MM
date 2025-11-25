package com.mm.farms.service;


import com.mm.farms.entity.Farm;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Service;

@Service
public interface FarmAuthService {
    void sendCode(String email);

    boolean verifyCode(String email, String code);

    boolean verifyOtp(String number,String otp);

    Farm farmLoginUsingPassword(String number,String password);

    void sendOtp(@Pattern(regexp = "^[0-9]{10,15}$", message = "Invalid contact number") String number);

}
