package com.mm.delivery.entity;


import com.mm.delivery.enums.VechileType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a delivery partner in the milk distribution system.
 * This entity is production-ready with proper constraints,
 * timestamps, and JPA mappings.
 */

@Entity
@Table(
        name = "milk_delivery_partners",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"contact_number"}),
                @UniqueConstraint(columnNames = {"email"}),
                @UniqueConstraint(columnNames = {"vehicle_no"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryPartner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", length = 100)
    @NotBlank(message = "Full name is required")
    private String fullName;

    @Column(name = "contact_number", nullable = false, length = 15, unique = true)
    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Invalid contact number")
    private String contactNumber;

    @Column(name = "email", length = 100, unique = true)
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "password", nullable = false)
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,25}$",
            message = "Password must be 8–25 characters long and include upper, lower, digit, and special character"
    )
    private String password;


    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 60, message = "Age must not exceed 60")
    private int age;

    @Column( length = 255)
    @NotBlank(message = "Address is required")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type",nullable = false, length = 30)
    private VechileType vechileType= VechileType.BIKE;

    @Column(name = "vehicle_no", unique = true, length = 20)
    @NotBlank(message = "Vehicle number is required")
    private String vehicleNo;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @OneToOne(mappedBy = "deliveryPartner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private LicenceImage licenceImage;

    @Column(name = "proof_of_identity", length = 100)
    private String proofOfIdentity;

    @Column(name = "proof_of_identity_image", length = 500)
    private String proofOfIdentityImage;

    @Column(nullable = false)
    private Long farmId;


    @Column(nullable = false)
    private Boolean locked = false;

    @Column(nullable = false)
    private Boolean verified = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
