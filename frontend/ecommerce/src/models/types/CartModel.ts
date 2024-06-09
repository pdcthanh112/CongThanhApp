import { Product } from "@/models/types";

export type Cart = {
  id: string;
  name: string;
  customerId: string;
  status: string;
  createdDate: Date;
  checkoutDate: Date;
  cartItems: CartItem[];
};

export type CartItem = {
  id: string;
  product: Product;
  quantity: number;
  status: string;
  cart?: string;
};
