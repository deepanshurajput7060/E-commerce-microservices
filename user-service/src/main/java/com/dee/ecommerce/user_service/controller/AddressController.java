package com.dee.ecommerce.user_service.controller;


import com.dee.ecommerce.user_service.dto.AddressRequest;
import com.dee.ecommerce.user_service.dto.AddressResponse;
import com.dee.ecommerce.user_service.dto.ApiResponse;
import com.dee.ecommerce.user_service.service.AddressService;
import com.dee.ecommerce.user_service.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users/me/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<Page<AddressResponse>> getAllAddresses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        String userId = SecurityUtils.getCurrentUserId();

        return ResponseEntity.ok(addressService.getAllAddresses(userId, page, size));
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponse> getAddress(
            @PathVariable Long addressId) {

        String userId = SecurityUtils.getCurrentUserId();

        return ResponseEntity.ok(addressService.getAddressById(userId, addressId));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createAddress(
            @Valid @RequestBody AddressRequest request) {

        String userId = SecurityUtils.getCurrentUserId();

        return ResponseEntity.ok(addressService.createAddress(userId, request));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse> updateAddress(
            @PathVariable Long addressId,
            @Valid @RequestBody AddressRequest request) {

        String userId = SecurityUtils.getCurrentUserId();

        return ResponseEntity.ok(addressService.updateAddress(userId, addressId, request));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse> deleteAddress(
            @PathVariable Long addressId) {

        String userId = SecurityUtils.getCurrentUserId();

        addressService.deleteAddress(userId, addressId);

        return ResponseEntity.ok(new ApiResponse("Address deleted successfully", true));
    }
}
