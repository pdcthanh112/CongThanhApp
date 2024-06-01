import { Cart } from "@models/type";

export type Checkout = {
  customer: string
  address: string;
  phone: string;
  total: number;
  payment: string;
  cart: Cart | string;
  voucher?: string;
}

export type CheckoutForm = {
  customer: string
  address: number;
  phone: string;
  total: number;
  payment: string;
  cart: Cart | string;
  voucher?: string;
}
