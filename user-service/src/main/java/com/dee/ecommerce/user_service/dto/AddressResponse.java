package com.dee.ecommerce.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor          // ✅ REQUIRED for ModelMapper
@AllArgsConstructor
public class AddressResponse {
    private Long addressId;
    private String houseNo;
    private String street;
    private String city;
    private String state;
    private String pincode;
    private String country;
    private boolean isDefault;
    private LocalDateTime createdAt;
}

