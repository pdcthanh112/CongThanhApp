import { Checkout, Product } from '@models/type';

export type Order = {
  id: number;
  customer: string;
  total: number;
  note?: string;
  orderDate: number;
  checkout: Checkout | string;
  status: string;
};

export type OrderDetail = {
  id: number;
  quantity: number;
  product: Product;
  status: string;
};
