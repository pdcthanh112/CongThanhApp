export type CreateAddressForm = {
  customer: string;
  phone: string;
  country: string;
  addressLine1: string;
  addressLine2: string;
  addressLine3: string;
  street: string;
  postalCode: string;
  isDefault: boolean;
};

export type UpdateAddressForm = {
  id: number;
  phone: string;
  country: string;
  addressLine1: string;
  addressLine2: string;
  addressLine3: string;
  street: string;
  postalCode: string;
};
