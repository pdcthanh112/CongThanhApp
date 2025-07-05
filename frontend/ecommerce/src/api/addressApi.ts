import axiosInstance from '@/config/axiosConfig';
import { CreateAddressForm, UpdateAddressForm } from '@/models/form';

export const getAddressById = async (addressId: number) => {
  return await axiosInstance.get(`address/${addressId}`).then((response) => response.data);
};

export const getAddressByCustomer = async (customerId: string) => {
  return await axiosInstance.get(`customer/${customerId}/address`).then((response) => response.data);
};

export const createAddress = async (data: CreateAddressForm) => {
  return await axiosInstance
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
    .then((response) => response.data);
};

export const updateAddress = async (addressId: number, data: UpdateAddressForm) => {
  return await axiosInstance
    .put(`address/${addressId}`, {
      phone: data.phone,
      country: data.country,
      addressLine1: data.addressLine1,
      addressLine2: data.addressLine2,
      addressLine3: data.addressLine3,
      street: data.street,
      postalCode: data.postalCode,
    })
    .then((response) => response.data);
};

export const deleteAddress = async (addressId: number) => {
  return await axiosInstance.delete(`address/${addressId}`).then((response) => response.data);
};

export const getDefaultAddressOfCustomer = async (customerId: string) => {
  return await axiosInstance.get(`customer/${customerId}/address/default`).then((response) => response.data);
};
