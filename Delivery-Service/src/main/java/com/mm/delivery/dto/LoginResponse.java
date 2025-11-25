package com.mm.delivery.dto;

import com.mm.delivery.entity.DeliveryPartner;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private DeliveryPartner deliveryPartner;
}
