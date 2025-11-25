package com.mm.customer.repository;

import com.mm.customer.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory,Long> {
    Optional<LoginHistory> findByCustomerId(Long id);

    Optional<LoginHistory> findFirstByCustomerIdAndIsLoginTrueOrderByLoginTimestampDesc(Long id);
}
