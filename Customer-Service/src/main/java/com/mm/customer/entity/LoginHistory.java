package com.mm.customer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer_login_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Establish relation instead of raw customerId
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "login_time_address")
    private String address;

    @CreationTimestamp
    @Column(name = "login_timestamp", updatable = false)
    private LocalDateTime loginTimestamp;

    @Column(name = "logout_timestamp")
    private LocalDateTime logoutTimestamp;

    @Column(name = "is_login")
    private Boolean isLogin = false;

    // Optional helper method to record logout
    public void markLogout() {
        this.logoutTimestamp = LocalDateTime.now();
        this.isLogin = false;
    }
}
