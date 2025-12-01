package com.dee.ecommerce.user_service.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter 
@Setter 
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class User {

    @Id
    private String userId;  // SAME AS AUTH SERVICE ID

    private String email;

    private String fullName;

    private String phone;

    private String gender;

    private LocalDate dob;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
