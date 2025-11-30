package com.mm.admin.service;

import com.mm.admin.response.AdminResponse;
import com.mm.admin.dto.CreateAdminRequest;
import com.mm.admin.dto.UpdateAdminRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {

    AdminResponse createAdmin(CreateAdminRequest request);

    AdminResponse updateAdmin(Long id, UpdateAdminRequest request);

    AdminResponse getAdmin(Long id);

    List<AdminResponse> getAllAdmins();

    Page<AdminResponse> getAdmins(Pageable pageable, String nameFilter);

    void deleteAdmin(Long id);
}
