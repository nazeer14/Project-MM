package com.mm.farms.entity;


import com.mm.farms.enums.TestResult;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a quality test report of milk collected from a specific farm.
 */
@Entity
@Table(name = "milk_test_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MilkTestReport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Farm that produced this milk batch.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    /**
     * Batch or collection ID for tracking.
     */
    @NotBlank(message = "Batch ID is required")
    @Column(nullable = false, unique = true, length = 50)
    private String batchId;

    /**
     * Date and time when the milk was tested.
     */
    @Column(nullable = false)
    private LocalDateTime testDate;

    /**
     * Fat content percentage.
     */
    @DecimalMin(value = "0.0", inclusive = false, message = "Fat % must be positive")
    @DecimalMax(value = "15.0", message = "Fat % cannot exceed 15%")
    @Column(nullable = false)
    private Double fatPercentage;

    /**
     * SNF (Solids-Not-Fat) content percentage.
     */
    @DecimalMin(value = "0.0", inclusive = false)
    @DecimalMax(value = "12.0")
    @Column(nullable = false)
    private Double snfPercentage;

    /**
     * Milk density (in kg/m³ or g/cm³).
     */
    @DecimalMin(value = "1.000", inclusive = false)
    @DecimalMax(value = "1.040")
    private Double density;

    /**
     * Temperature of milk during testing (°C).
     */
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "50.0")
    private Double temperature;

    /**
     * Water adulteration percentage (if any).
     */
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private Double waterContent;

    /**
     * Indicates if milk passed the quality test.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private TestResult result;

    /**
     * Remarks about test quality or issues.
     */
    @Column(length = 255)
    private String remarks;

    /**
     * Created timestamp.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.testDate = LocalDateTime.now();
        this.result = evaluateResult();
    }

    /**
     * Evaluates the milk test based on fat and SNF thresholds.
     */
    private TestResult evaluateResult() {
        if (this.fatPercentage >= 3.5 && this.snfPercentage >= 8.5 && this.waterContent <= 5.0) {
            return TestResult.PASSED;
        }
        return TestResult.FAILED;
    }
}

