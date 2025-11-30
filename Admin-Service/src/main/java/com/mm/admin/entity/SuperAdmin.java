package com.mm.admin.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "super_admin")
@Data
@Builder
public class SuperAdmin implements Serializable {

    @Id
    private Long id;

    @Column(nullable = false,unique = true)
    private String adminCode;

    @Column(nullable = false,unique = true)
    private String number;

    private String password;

    @Column(nullable = false,unique = true)
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private int version;
}
