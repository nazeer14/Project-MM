package com.mm.bookings.client;

import com.mm.bookings.response.FarmResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name= "farm-service" )
public interface FarmClient {

    @GetMapping("/api/v1/farm/{id}")
    FarmResponse getFarmById(@PathVariable Long id);
}
