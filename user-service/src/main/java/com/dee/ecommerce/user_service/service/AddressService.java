package com.dee.ecommerce.user_service.service;

import com.dee.ecommerce.user_service.dto.AddressRequest;
import com.dee.ecommerce.user_service.dto.AddressResponse;
import com.dee.ecommerce.user_service.dto.ApiResponse;
import org.springframework.data.domain.Page;

public interface AddressService {

    Page<AddressResponse> getAllAddresses(String userId, int page, int size);

    AddressResponse getAddressById(String userId, Long addressId);

    ApiResponse createAddress(String userId, AddressRequest request);

    ApiResponse updateAddress(String userId, Long addressId, AddressRequest request);

    void deleteAddress(String userId, Long addressId);
}
