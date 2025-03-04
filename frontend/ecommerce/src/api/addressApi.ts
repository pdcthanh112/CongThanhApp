import axiosConfig from '@/config/axiosConfig';
import { CreateAddressForm, UpdateAddressForm } from '@/models/form';

export const getAddressById = async (addressId: number) => {
  return await axiosConfig
    .get(`address/${addressId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const getAddressByCustomer = async (customerId: string) => {
  return await axiosConfig
    .get(`customer/${customerId}/address`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};

export const createAddress = async (data: CreateAddressForm) => {
  return await axiosConfig
    .post('address', {
      customer: data.customer,
      phone: data.phone,
      country: data.country,
      addressLine1: data.addressLine1,
      addressLine2: data.addressLine2,
      addressLine3: data.addressLine3,
      street: data.street,
      postalCode: data.postalCode,
      idDefault: data.isDefault,
    })
    .then((response) => response.data)
    .catch((error) => {
      throw new error;
    });
};

export const updateAddress = async (addressId: number, data: UpdateAddressForm) => {
  return await axiosConfig
    .put(`address/${addressId}`, {
      phone: data.phone,
      country: data.country,
      addressLine1: data.addressLine1,
      addressLine2: data.addressLine2,
      addressLine3: data.addressLine3,
      street: data.street,
      postalCode: data.postalCode,
    })
    .then((response) => response.data)
    .catch((error) => {
      throw new error;
    });
};

export const deleteAddress = async (addressId: number) => {
  return await axiosConfig
    .delete(`address/${addressId}`)
    .then((response) => response.data)
    .catch((error) => {
      throw new error;
    });
};

export const getDefaultAddressOfCustomer = async (customerId: string) => {
  return await axiosConfig
    .get(`customer/${customerId}/address/default`)
    .then((response) => response.data)
    .catch((error) => {
      throw error;
    });
};
