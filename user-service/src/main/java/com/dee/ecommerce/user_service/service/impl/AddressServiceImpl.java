package com.dee.ecommerce.user_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dee.ecommerce.user_service.dto.AddressRequest;
import com.dee.ecommerce.user_service.dto.AddressResponse;
import com.dee.ecommerce.user_service.dto.ApiResponse;
import com.dee.ecommerce.user_service.entity.Address;
import com.dee.ecommerce.user_service.entity.User;
import com.dee.ecommerce.user_service.exception.ResourceNotFoundException;
import com.dee.ecommerce.user_service.repository.AddressRepository;
import com.dee.ecommerce.user_service.repository.UserRepository;
import com.dee.ecommerce.user_service.service.AddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {

    private static final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    // ======================================================
    // GET ALL ADDRESSES (PAGINATED)
    // ======================================================
    @Override
    @Transactional(readOnly = true)
    public Page<AddressResponse> getAllAddresses(String userId, int page, int size) {

        logger.info("Fetching addresses | userId={} page={} size={}", userId, page, size);

        User user = validateUserExist(userId);

        PageRequest pageRequest = PageRequest.of(page, size);
        logger.debug("Created PageRequest: {}", pageRequest);

        Page<Address> addressPage =
                addressRepository.findByUserUserId(user.getUserId(), pageRequest);

        List<AddressResponse> addressResponses = addressPage.getContent()
                .stream()
                .map(address -> mapper.map(address, AddressResponse.class))
                .collect(Collectors.toList());

        Page<AddressResponse> responsePage = new PageImpl<>(
                addressResponses,
                pageRequest,
                addressPage.getTotalElements()
        );

        logger.info("Addresses fetched successfully | totalElements={}",
                responsePage.getTotalElements());

        return responsePage;
    }

    // ======================================================
    // GET ADDRESS BY ID
    // ======================================================
    @Override
    @Transactional(readOnly = true)
    public AddressResponse getAddressById(String userId, Long addressId) {

        logger.info("Fetching address | userId={} addressId={}", userId, addressId);

        Address address = validateAddressOwnership(userId, addressId);

        AddressResponse response = mapper.map(address, AddressResponse.class);
        logger.debug("Mapped AddressResponse: {}", response);

        return response;
    }

    // ======================================================
    // CREATE ADDRESS
    // ======================================================
    @Override
    public ApiResponse createAddress(String userId, AddressRequest request) {

        logger.info("Creating address | userId={}", userId);
        logger.debug("Incoming request: {}", request);

        User user = validateUserExist(userId);

        Address address = mapper.map(request, Address.class);
        address.setUser(user);

        Address savedAddress = addressRepository.save(address);
        logger.info("Address created | addressId={}", savedAddress.getAddressId());

        ApiResponse response = new ApiResponse("Address created successfully", true);
        return response;
    }

    // ======================================================
    // UPDATE ADDRESS
    // ======================================================
    @Override
    public ApiResponse updateAddress(String userId, Long addressId, AddressRequest request) {

        logger.info("Updating address | userId={} addressId={}", userId, addressId);
        logger.debug("Update request: {}", request);

        Address address = validateAddressOwnership(userId, addressId);

        mapper.map(request, address);

        Address updatedAddress = addressRepository.save(address);
        logger.info("Address updated | addressId={}", updatedAddress.getAddressId());

        ApiResponse response = new ApiResponse("Address updated successfully", true);
        return response;
    }

    // ======================================================
    // DELETE ADDRESS
    // ======================================================
    @Override
    public void deleteAddress(String userId, Long addressId) {

        logger.info("Deleting address | userId={} addressId={}", userId, addressId);

        Address address = validateAddressOwnership(userId, addressId);

        addressRepository.delete(address);
        logger.info("Address deleted | addressId={}", addressId);
    }

    // ======================================================
    // HELPER METHODS
    // ======================================================

    private User validateUserExist(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.error("User not found | userId={}", userId);
                    return new ResourceNotFoundException("User", "Id", userId);
                });
    }

    private Address validateAddressOwnership(String userId, Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> {
                    logger.error("Address not found | addressId={}", addressId);
                    return new ResourceNotFoundException(
                            "Address",
                            "Id",
                            addressId.toString()
                    );
                });

        if (!address.getUser().getUserId().equals(userId)) {
            logger.error("Address ownership violation | addressId={} userId={}",
                    addressId, userId);
            throw new ResourceNotFoundException(
                    "Address",
                    "UserId",
                    userId
            );
        }

        logger.debug("Address ownership validated | addressId={} userId={}",
                addressId, userId);

        return address;
    }
}
