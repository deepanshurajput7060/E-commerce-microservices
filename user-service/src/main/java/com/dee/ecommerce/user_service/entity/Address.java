package com.dee.ecommerce.user_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_addresses",
        indexes = {
                // MOST IMPORTANT (foreign key lookup)
                @Index(name = "idx_address_user", columnList = "user_id"),
                // default address lookup
                @Index(name = "idx_address_default", columnList = "is_default"),
                // search filters
                @Index(name = "idx_address_city", columnList = "city"),
                @Index(name = "idx_address_pincode", columnList = "pincode"),
                // sorting / pagination
                @Index(name = "idx_address_created_at", columnList = "created_at")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "House No is required")
    @Column(nullable = false) // Added database constraint
    private String houseNo;

    @NotBlank(message = "Street is required")
    @Size(max = 150)
    @Column(nullable = false, length = 150) // Synced size and nullability
    private String street;

    @NotBlank(message = "City is required")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String state;

    @NotBlank(message = "Pincode is required")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Invalid pincode...")
    @Column(nullable = false, length = 6) // Enforced 6 chars in DB
    private String pincode;

    @NotBlank(message = "Country is required")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String country;

    @Builder.Default
    @Column(name = "is_default", nullable = false) // Boolean should also be NOT NULL
    private boolean isDefault = false;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
