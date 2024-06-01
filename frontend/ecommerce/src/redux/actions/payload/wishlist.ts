import { Wishlist } from '@models/type';

export interface FetchWishlistRequestedPayload {
  customerId: string;
}

export interface FetchWishlistStartPayload {
  params: { token: string };
}

export interface FetchWishlistSucceededPayload {
  data: Wishlist;
}

export interface FetchWishlistFailedPayload {
  error: string;
}

export interface AddItemToWishlistRequestedPayload {
  customerId: string;
  productId: string;
}

export interface AddItemToWishlistStartPayload {
  params: { customerId: string; productId: string };
}

export interface AddItemToWishlistSucceededPayload {
  data: Wishlist;
}

export interface AddItemToWishlistFailedPayload {
    errorCode: number;
    message: string;
}

export interface AddItemToWishlistCleanPayload {
  data: string;
}

export interface RemoveItemFromWishlistRequestedPayload {
  customerId: string;
  productId: string;
}

export interface RemoveItemFromWishlistStartPayload {
  params: { customerId: string; productId: string };
}

export interface RemoveItemFromWishlistSucceededPayload {
  data: Wishlist;
}

export interface RemoveItemFromWishlistFailedPayload {
  error: {
    errorCode: number;
    message: string;
  };
}

export interface RemoveItemFromWishlistCleanPayload {
  data: string;
}
