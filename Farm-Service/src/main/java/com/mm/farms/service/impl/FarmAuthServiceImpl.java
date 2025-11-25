package com.mm.farms.service.impl;

import com.mm.farms.entity.Farm;
import com.mm.farms.repository.FarmRepository;
import com.mm.farms.service.FarmAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class FarmAuthServiceImpl implements FarmAuthService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FarmRepository farmRepository;

    // Store OTP temporarily (for demo; use Redis or DB in production)
    private final Map<String, String> otpStorage = new HashMap<>();

    public void sendCode(String email) {
        String otp = generateOtp();
        otpStorage.put(email, otp);
        sendEmail(email, otp);
    }

    private String generateOtp() {
        SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }

    private void sendEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Verification Code");
        message.setText("Your OTP code is: " + otp + "\n\nValid for 5 minutes.");
        message.setFrom("your_email@gmail.com"); // Must match spring.mail.username

        mailSender.send(message);
    }

    public boolean verifyCode(String email, String enteredOtp) {
        String correctOtp = otpStorage.get(email);
        if (correctOtp != null && correctOtp.equals(enteredOtp)) {
            otpStorage.remove(email);
            return true;
        }
        return false;
    }

    @Override
    public boolean verifyOtp(String number, String otp) {
        String correctOtp = otpStorage.get(number);
        if (correctOtp != null && correctOtp.equals(otp)) {
            otpStorage.remove(number);
            return true;
        }
        return false;
    }




    @Override
    public void sendOtp(String number) {
        String otp=generateOtp();
        otpStorage.put(number, otp);
        log.info("{} otp {}",number,otp);
        //send otp in real time using firebase or twilio

    }


    @Override
    public Farm farmLoginUsingPassword(String number, String password) {
        try{
            Farm farm=farmRepository.findByContactNumber(number)
                    .orElseThrow(()->new IllegalArgumentException("Invalid Number"));
            //verify password
        }catch (RuntimeException e){
            throw new RuntimeException(e.getCause());
        }
        return null;
    }
}
