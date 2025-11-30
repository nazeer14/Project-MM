package com.mm.admin.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "admins",
        indexes = {
                @Index(name = "idx_employee_id", columnList = "employeeId"),
                @Index(name = "idx_number", columnList = "number")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE admins SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 30)
    private String employeeId;

    @Column(length = 100)
    private String officeLocation;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(unique = true, nullable = false, length = 15)
    private String number;

    @Column(nullable = false)
    private String password; // hashed

    @Column(length = 150)
    private String location;

    @Column(length = 200)
    private String address;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Boolean verified = false;

    @Column(nullable = false)
    private Boolean locked = false;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Version
    private int version;
}
