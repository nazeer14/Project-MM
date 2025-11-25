package com.mm.delivery.service.impl;

import com.mm.delivery.entity.DeliveryPartner;
import com.mm.delivery.repository.DeliveryPartnerRepo;
import com.mm.delivery.service.DeliveryPartnerService;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    private final DeliveryPartnerRepo deliveryPartnerRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DeliveryPartner createPartner(DeliveryPartner deliveryPartner) {
        deliveryPartner.setPassword(passwordEncoder.encode(deliveryPartner.getPassword()));
        return deliveryPartnerRepo.save(deliveryPartner);
    }

    @Override
    public DeliveryPartner getByPartnerId(Long id) {
        return deliveryPartnerRepo.findById(id).orElseThrow(() -> new NotFoundException("Partner not found with Id : "+id));
    }

    @Override
    public DeliveryPartner getByNumber(String number){
        return deliveryPartnerRepo.findByContactNumber(number).orElseThrow(()->new NotFoundException("Partner Not found with Number : "+number));
    }

    @Override
    public boolean existsByNumber(String number){
        return deliveryPartnerRepo.existsByContactNumber(number);
    }

    @Override
    public boolean existsById(Long id){
        return deliveryPartnerRepo.existsById(id);
    }

    @Override
    public DeliveryPartner update(DeliveryPartner deliveryPartner) {
        return deliveryPartnerRepo.save(deliveryPartner);
    }

    @Override
    public DeliveryPartner verifyWithPassword(String number, String rawPassword) {
        Optional<DeliveryPartner> dp=deliveryPartnerRepo.findByContactNumber(number);
        if(dp.isEmpty()) return null;
        DeliveryPartner deliveryPartner=dp.get();
        String storedPassword = deliveryPartner.getPassword();
        boolean isMatch= passwordEncoder.matches(rawPassword,storedPassword);
        if(isMatch)
            return deliveryPartner;
        else
            throw new BadRequestException("Invalid Password");
    }


}
