package com.mm.customer.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "customer_name",length = 50)
    private String name;

    @Column(name = "contact_number", nullable = false, unique = true)
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid contact number")
    private String contactNumber;

    private String location;

    @ElementCollection
    @CollectionTable(name = "customer_addresses", // New table name
            joinColumns = @JoinColumn(name = "customer_id")) // Foreign key column
    @Column(name = "address_line", length = 300) // Column in the new table
    private List<String> address;

    @Column(name = "is_verified")
    private Boolean verified = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_login")
    private Boolean isLogin = false;

    @Column(name = "is_locked")
    private Boolean locked=false;

    @Version
    private Integer version;



}
