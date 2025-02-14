import { Cart } from '@/models/types/CartModel';

export type FetchCartRequestedPayload = {
  values: { id: string; name: string };
};

export type FetchCartStartPayload = {
  values: { id: string; name: string };
};

export type FetchCartSucceededPayload = {
  data: Cart[];
};

export type FetchCartFailedPayload = {
  error: string;
};
export type FetchCartClearPayload = {
  data: {};
};
export type CreateNewCartRequestedPayload = {
  values: { id: string; name: string };
};

export type CreateNewCartStartPayload = {
  values: { id: string; name: string };
};

export type CreateNewCartSucceededPayload = {
  data: {};
};

export type CreateNewCartFailedPayload = {
  error: string;
};

export type DeleteCartRequestedPayload = {
  values: { id: string };
};

export type DeleteCartStartPayload = {
  values: { id: string };
};

export type DeleteCartSucceededPayload = {
  data: {};
};

export type DeleteCartFailedPayload = {
  error: string;
};

export type AddItemToCartRequestedPayload = {
  values: { productId: string; quantity: number; cartId: string };
};

export type AddItemToCartStartPayload = {
  product: any;
  quantity: any;
  values: { productId: string; quantity: number; cartId: string };
};

export type AddItemToCartSucceededPayload = {
  data: {};
};

export type AddItemToCartFailedPayload = {
  error: string;
};

export type RemoveItemFromCartRequestedPayload = {
  values: { productId: string; cartId: string };
};

export type RemoveItemFromCartStartPayload = {
  values: { productId: string; cartId: string };
};

export type RemoveItemFromCartSucceededPayload = {
  token: string;
};

export type RemoveItemFromCartFailedPayload = {
  error: string;
};

export type updateItemQuantityRequestPayload = {
  values: {};
};

export type updateItemQuantityStartPayload = {
  values: { cartId: number; itemId: number; quantity: number };
};

export type updateItemQuantitySucceededPayload = {
  data: {};
};

export type updateItemQuantityFailedPayload = {
  error: string;
};

export type updateItemQuantityClearPayload = {
  data: {};
};
