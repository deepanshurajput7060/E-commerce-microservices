package com.dee.ecommerce.user_service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.dee.ecommerce.user_service.constant.Gender;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {

    private String userId;
    private String email;
    private String fullName;
    private String phone;
    private Gender gender;
    private LocalDate dob;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
