package com.dee.ecommerce.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class AddressRequest {

    @NotBlank(message = "House No is required")
    private String houseNo;

    @NotBlank(message = "Street is required")
    @Size(max = 150)
    private String street;

    @NotBlank(message = "City is required")
    @Size(max = 50)
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 50)
    private String state;

    @NotBlank(message = "Pincode is required")
    @Pattern(
            regexp = "^[1-9][0-9]{5}$",
            message = "Invalid pincode. Must be exactly 6 digits (0-9) and cannot start with 0."
    )
    private String pincode;

    @NotBlank(message = "Country is required")
    @Size(max = 50)
    private String country;

    private boolean isDefault;
}

