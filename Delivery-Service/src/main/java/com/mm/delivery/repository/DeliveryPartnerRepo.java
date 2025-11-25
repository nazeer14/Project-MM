package com.mm.delivery.repository;

import com.mm.delivery.entity.DeliveryPartner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryPartnerRepo extends JpaRepository<DeliveryPartner,Long> {

    Optional<DeliveryPartner> findByContactNumber(String number);

    boolean existsByContactNumber(String number);

}
