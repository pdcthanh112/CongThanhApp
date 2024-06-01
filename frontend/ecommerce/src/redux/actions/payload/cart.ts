import { Cart } from "@models/type/CartModel";

export interface FetchCartRequestedPayload {
  values: { id: string; name: string };
}

export interface FetchCartStartPayload {
  values: { id: string; name: string };
}

export interface FetchCartSucceededPayload {
  data: Cart[];
}

export interface FetchCartFailedPayload {
  error: string;
}
export interface FetchCartClearPayload {
  data: {};
}
export interface CreateNewCartRequestedPayload {
  values: { id: string; name: string };
}

export interface CreateNewCartStartPayload {
  values: { id: string; name: string };
}

export interface CreateNewCartSucceededPayload {
  data: {};
}

export interface CreateNewCartFailedPayload {
  error: string;
}

export interface DeleteCartRequestedPayload {
  values: { id: string };
}

export interface DeleteCartStartPayload {
  values: { id: string };
}

export interface DeleteCartSucceededPayload {
  data: {};
}

export interface DeleteCartFailedPayload {
  error: string;
}

export interface AddItemToCartRequestedPayload {
  values: { productId: string; quantity: number; cartId: string };
}

export interface AddItemToCartStartPayload {
  values: { productId: string; quantity: number; cartId: string };
}

export interface AddItemToCartSucceededPayload {
  data: {};
}

export interface AddItemToCartFailedPayload {
  error: string;
}

export interface RemoveItemFromCartRequestedPayload {
  values: { productId: string; cartId: string };
}

export interface RemoveItemFromCartStartPayload {
  values: { productId: string; cartId: string };
}

export interface RemoveItemFromCartSucceededPayload {
  token: string;
}

export interface RemoveItemFromCartFailedPayload {
  error: string;
}
