package com.mm.admin.service.impl;

import com.mm.admin.response.AdminResponse;
import com.mm.admin.dto.CreateAdminRequest;
import com.mm.admin.dto.UpdateAdminRequest;
import com.mm.admin.entity.Admin;
import com.mm.admin.repository.AdminRepository;
import com.mm.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AdminResponse createAdmin(CreateAdminRequest request) {
        Admin admin = Admin.builder()
                .employeeId(request.getEmployeeId())
                .name(request.getName())
                .number(request.getNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .officeLocation(request.getOfficeLocation())
                .location(request.getLocation())
                .address(request.getAddress())
                .verified(false)
                .locked(false)
                .build();

        adminRepository.save(admin);
        return mapToResponse(admin);
    }

    @Override
    public Page<AdminResponse> getAdmins(Pageable pageable, String nameFilter) {
        Page<Admin> page;
        if (nameFilter == null || nameFilter.isBlank()) {
            page = adminRepository.findAll(pageable);
        } else {
            page = adminRepository.findByNameContainingIgnoreCase(nameFilter, pageable);
        }
        return page.map(this::mapToResponse);
    }

    @Override
    public AdminResponse updateAdmin(Long id, UpdateAdminRequest request) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found: " + id));

        if (request.getName() != null) admin.setName(request.getName());
        if (request.getNumber() != null) admin.setNumber(request.getNumber());
        if (request.getOfficeLocation() != null) admin.setOfficeLocation(request.getOfficeLocation());
        if (request.getLocation() != null) admin.setLocation(request.getLocation());
        if (request.getAddress() != null) admin.setAddress(request.getAddress());
        if (request.getVerified() != null) admin.setVerified(request.getVerified());
        if (request.getLocked() != null) admin.setLocked(request.getLocked());

        adminRepository.save(admin);
        return mapToResponse(admin);
    }

    @Override
    public AdminResponse getAdmin(Long id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found: " + id));

        return mapToResponse(admin);
    }

    @Override
    public List<AdminResponse> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new RuntimeException("Admin not found: " + id);
        }
        adminRepository.deleteById(id);
    }

    private AdminResponse mapToResponse(Admin admin) {
        AdminResponse response = new AdminResponse();
        response.setId(admin.getId());
        response.setEmployeeId(admin.getEmployeeId());
        response.setName(admin.getName());
        response.setNumber(admin.getNumber());
        response.setOfficeLocation(admin.getOfficeLocation());
        response.setLocation(admin.getLocation());
        response.setAddress(admin.getAddress());
        response.setVerified(admin.getVerified());
        response.setLocked(admin.getLocked());
        return response;
    }
}
