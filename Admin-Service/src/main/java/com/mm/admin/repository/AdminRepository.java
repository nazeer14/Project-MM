package com.mm.admin.repository;

import com.mm.admin.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

    Optional<Admin> findByEmployeeId(String employeeId);

    Optional<Admin> findByNumber(String number);

    Page<Admin> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
