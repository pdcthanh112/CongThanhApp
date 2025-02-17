import { Product } from '@/models/types';

export type Cart = {
  product: string;
  quantity: number;
  id: number;
  name: string;
  customerId: string;
  status: string;
  createdDate: Date;
  checkoutDate: Date;
  cartItems: CartItem[];
};

export type CartItem = {
  id: number;
  product: Product;
  quantity: number;
  status: string;
  cart?: number;
};
