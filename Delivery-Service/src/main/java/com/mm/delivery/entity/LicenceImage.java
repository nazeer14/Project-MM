package com.mm.delivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a delivery partner's licence details and verification status.
 * This entity is linked one-to-one with DeliveryPartner.
 */
@Entity
@Table(
        name = "rider_licence_image",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"licence_number"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LicenceImage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * One-to-One mapping with DeliveryPartner.
     * A delivery partner can have exactly one licence record.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_partner_id", nullable = false, unique = true)
    private DeliveryPartner deliveryPartner;

    @Column(name = "licence_image_url", length = 500, nullable = false)
    @NotBlank(message = "Licence image URL is required")
    private String licenceImageUrl;

    @Column(name = "licence_number", nullable = false, unique = true, length = 50)
    @NotBlank(message = "Licence number is required")
    private String licenceNumber;

    @Column(name = "vehicle_number", nullable = false, unique = true, length = 50)
    @NotBlank(message = "Vehicle number is required")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$",message = "Invalid vehicle number")
    private String vehicleNumber;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
