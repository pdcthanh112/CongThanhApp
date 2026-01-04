package com.congthanh.orderservice.model.request;

public record ShippingAddressRequest(String fullName, String phoneNumber, String country, String addressLine1,
                                     String addressLine2, String addressLine3, String street, String postalCode) {
}
