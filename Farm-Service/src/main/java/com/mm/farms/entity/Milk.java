package com.mm.farms.entity;

import com.mm.farms.enums.DayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents the milk production details for a specific farm per day and session (morning/evening).
 */
@Entity
@Table(name = "milk_per_day",
        uniqueConstraints = @UniqueConstraint(columnNames = {"farm_id", "day_type", "date"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Milk implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The farm from which this milk was produced.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farm_id", nullable = false)
    private Farm farm;

    /**
     * Indicates whether milk was collected in the MORNING or EVENING.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "day_type", nullable = false, length = 20)
    private DayType dayType;

    /**
     * The date when milk was produced/collected.
     */
    @NotNull(message = "Date cannot be null")
    @Column(nullable = false, updatable = false)
    private LocalDate date;

    /**
     * Quantity of milk produced in liters.
     */
    @NotNull(message = "Produced milk quantity is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Produced milk must be positive")
    private Double producedMilk;

    /**
     * Associated test report for this batch/day.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "milk_test_report_id")
    private MilkTestReport milkTestReport;

    /**
     * Timestamp for last update.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Automatically set update time.
     */
    @PrePersist
    public void onCreate() {
        this.date = (this.date == null) ? LocalDate.now() : this.date;
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
