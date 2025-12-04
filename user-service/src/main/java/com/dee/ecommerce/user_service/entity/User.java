package com.dee.ecommerce.user_service.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dee.ecommerce.user_service.constant.Gender;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@EntityListeners (AuditingEntityListener.class)
public class User {

    @Id
    private String userId;  // SAME AS AUTH SERVICE ID

    private String email;

    private String fullName;

    private String phone;

    @Enumerated (EnumType.STRING)
    private Gender gender;

    private LocalDate dob;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
