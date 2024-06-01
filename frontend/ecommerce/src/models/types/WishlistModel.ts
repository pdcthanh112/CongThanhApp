import { Product } from "@models/type";

export type Wishlist = {
  id: string;
  custommer: string;
  product: Product[];
}
