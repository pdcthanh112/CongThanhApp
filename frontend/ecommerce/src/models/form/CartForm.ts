export type CreateCartForm = {
  name: string;
  customer: string;
  isDefault: boolean;
};

export type AddToCartForm = {
  productId: any;
  quantity: number;
  cartId: string;
};

export type UpdateCartItemForm = {
  quantity: number;
  itemId: string;
};
