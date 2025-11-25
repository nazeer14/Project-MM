package com.mm.delivery.service;

import com.mm.delivery.entity.DeliveryPartner;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public interface DeliveryPartnerService {

    DeliveryPartner createPartner(DeliveryPartner deliveryPartner);

    DeliveryPartner getByPartnerId(Long id);

    DeliveryPartner getByNumber(String number);

    boolean existsByNumber(String number);

    boolean existsById(Long id);

    DeliveryPartner update(DeliveryPartner deliveryPartner);

    DeliveryPartner verifyWithPassword(String number, String password);
}
