package com.dee.ecommerce.user_service.controller;


import com.dee.ecommerce.user_service.dto.AddressRequest;
import com.dee.ecommerce.user_service.dto.AddressResponse;
import com.dee.ecommerce.user_service.dto.ApiResponse;
import com.dee.ecommerce.user_service.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<Page<AddressResponse>> getAllAddresses(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(addressService.getAllAddresses(userId, page, size));
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddress(
            @PathVariable String userId,
            @PathVariable Long addressId) {

        return ResponseEntity.ok(addressService.getAddressById(userId, addressId));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAddress(
            @PathVariable String userId,
            @Valid @RequestBody AddressRequest request) {

        return ResponseEntity.ok(addressService.createAddress(userId, request));
    }

    @PutMapping("/{addressId}/update")
    public ResponseEntity<ApiResponse> updateAddress(
            @PathVariable String userId,
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest request) {

        return ResponseEntity.ok(addressService.updateAddress(userId, addressId, request));
    }

    @DeleteMapping("{addressId}/delete")
    public ResponseEntity<ApiResponse> deleteAddress(
            @PathVariable String userId,
            @PathVariable Long addressId) {

        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.ok(new ApiResponse("Address deleted successfully", true));
    }
}
