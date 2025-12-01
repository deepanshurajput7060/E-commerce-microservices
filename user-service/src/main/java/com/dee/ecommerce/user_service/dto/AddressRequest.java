package com.dee.ecommerce.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor
public class AddressRequest {

    @NotBlank(message = "Street cannot be empty")
    private String street;

    @NotBlank(message = "City cannot be empty")
    private String city;

    @NotBlank(message = "State cannot be empty")
    private String state;

    @NotBlank(message = "Pincode cannot be empty")
    @Pattern(
            regexp = "^[1-9][0-9]{2}\\s?[0-9]{3}$",
            message = "Pincode must be a valid 6-digit postal code"
        )
    private String pincode;

    @NotBlank(message = "Country cannot be empty")
    private String country;
}

