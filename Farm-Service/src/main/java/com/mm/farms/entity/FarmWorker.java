package com.mm.farms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Represents a worker employed at a farm.
 * Each worker is associated with exactly one farm.
 */
@Entity
@Table(
        name = "farm_workers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"contact_number"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FarmWorker {

    /**
     * Primary key for each worker (auto-generated).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Farm to which the worker belongs (many workers can belong to one farm).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    /**
     * Full name of the worker.
     */
    @NotBlank(message = "Name is required")
    @Column(nullable = false, length = 100)
    private String fullName;

    /**
     * Contact number of the worker.
     */
    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Invalid contact number")
    @Column(name = "contact_number", nullable = false, unique = true, length = 15)
    private String contactNumber;

    /**
     * Type of proof used for identity (Aadhaar, PAN, etc.).
     */
    @Column(name = "proof_of_identity", length = 100)
    private String proofOfIdentity;

    /**
     * Image URL of the proof of identity.
     */
    @Column(name = "proof_of_identity_image", length = 500)
    private String proofOfIdentityImage;

    /**
     * Profile image of the worker.
     */
    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;

    /**
     * Whether the worker is blocked from the system.
     */
    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked = false;

    /**
     * Whether the worker is verified by the admin.
     */
    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    /**
     * Automatically captures creation time.
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Automatically updates timestamp on modification.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
