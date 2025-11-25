package com.mm.farms.entity;

import com.mm.farms.enums.FarmStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a dairy farm registered in the MAA MILK system.
 * Each farm holds details about its location, livestock, and ownership.
 */
@Entity
@Table(
        name = "farms",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"lat", "lon"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Farm implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Farm name is required")
    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 20)
    private String areaZone;

    @NotBlank(message = "Address is required")
    @Column(nullable = false, length = 255)
    private String address;

    @NotBlank(message = "Latitude is required")
    @Column(nullable = false, length = 50)
    private String lat;

    @NotBlank(message = "Longitude is required")
    @Column(nullable = false, length = 50)
    private String lon;

    @Column(length = 50)
    private String area;

    @Min(value = 1, message = "Farm must have at least one unit")
    @Column(name = "no_of_units")
    private Integer noOfUnits;

    @NotBlank(message = "Owner name is required")
    @Column(nullable = false, length = 100)
    private String ownerName;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be 10 digits")
    @Column(length = 15)
    private String contactNumber;

    @Email(message = "Invalid email format")
    @Column(length = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,25}$",
            message = "Password must be 8–25 characters long and include upper, lower, digit, and special character"
    )
    private transient String password;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String images;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private FarmStatus status = FarmStatus.PENDING;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Column(name = "delivery_partner_ids")
    private List<Long> deliveryPartnerIds;
}
