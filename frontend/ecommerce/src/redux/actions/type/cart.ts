import { Cart } from '@models/type/CartModel';
import * as actionName from '../name/cart';
import {
  AddItemToCartFailedPayload,
  AddItemToCartRequestedPayload,
  AddItemToCartStartPayload,
  AddItemToCartSucceededPayload,
  CreateNewCartFailedPayload,
  CreateNewCartRequestedPayload,
  CreateNewCartStartPayload,
  CreateNewCartSucceededPayload,
  DeleteCartFailedPayload,
  DeleteCartRequestedPayload,
  DeleteCartStartPayload,
  DeleteCartSucceededPayload,
  FetchCartClearPayload,
  FetchCartFailedPayload,
  FetchCartRequestedPayload,
  FetchCartStartPayload,
  FetchCartSucceededPayload,
  RemoveItemFromCartFailedPayload,
  RemoveItemFromCartRequestedPayload,
  RemoveItemFromCartStartPayload,
  RemoveItemFromCartSucceededPayload,
} from '../payload/cart';

export interface CartState {
  status: 'idle' | 'pending' | 'succeeded' | 'failed'
  error: string | null;
  data: Cart[];
}

export type FetchCartRequested = {
  type: typeof actionName.FETCH_CART_REQUESTED;
  payload: FetchCartRequestedPayload;
};

export type FetchCartStart = {
  type: typeof actionName.FETCH_CART_START;
  payload: FetchCartStartPayload;
};

export type FetchCartSucceeded = {
  type: typeof actionName.FETCH_CART_SUCCEEDED;
  payload: FetchCartSucceededPayload;
};

export type FetchCartFailed = {
  type: typeof actionName.FETCH_CART_FAILED;
  payload: FetchCartFailedPayload;
};

export type FetchCartClear = {
  type: typeof actionName.FETCH_CART_CLEAR;
  payload: FetchCartClearPayload;
};

export type CreateNewCartRequested = {
  type: typeof actionName.CREATE_NEW_CART_REQUESTED;
  payload: CreateNewCartRequestedPayload;
};

export type CreateNewCartStart = {
  type: typeof actionName.CREATE_NEW_CART_START;
  payload: CreateNewCartStartPayload;
};

export type CreateNewCartSucceeded = {
  type: typeof actionName.CREATE_NEW_CART_SUCCEEDED;
  payload: CreateNewCartSucceededPayload;
};

export type CreateNewCartFailed = {
  type: typeof actionName.CREATE_NEW_CART_FAILED;
  payload: CreateNewCartFailedPayload;
};

export type DeleteCartRequested = {
  type: typeof actionName.DELETE_CART_REQUESTED;
  payload: DeleteCartRequestedPayload;
};

export type DeleteCartStart = {
  type: typeof actionName.DELETE_CART_START;
  payload: DeleteCartStartPayload;
};

export type DeleteCartSucceeded = {
  type: typeof actionName.DELETE_CART_SUCCEEDED;
  payload: DeleteCartSucceededPayload;
};

export type DeleteCartFailed = {
  type: typeof actionName.DELETE_CART_FAILED;
  payload: DeleteCartFailedPayload;
};

export type AddItemToCartRequested = {
  type: typeof actionName.ADD_ITEM_TO_CART_REQUESTED;
  payload: AddItemToCartRequestedPayload;
};

export type AddItemToCartStart = {
  type: typeof actionName.ADD_ITEM_TO_CART_START;
  payload: AddItemToCartStartPayload;
};

export type AddItemToCartSucceeded = {
  type: typeof actionName.ADD_ITEM_TO_CART_SUCCEEDED;
  payload: AddItemToCartSucceededPayload;
};

export type AddItemToCartFailed = {
  type: typeof actionName.ADD_ITEM_TO_CART_FAILED;
  payload: AddItemToCartFailedPayload;
};

export type RemoveItemFromCartRequested = {
  type: typeof actionName.REMOVE_ITEM_FROM_CART_REQUESTED;
  payload: RemoveItemFromCartRequestedPayload;
};

export type RemoveItemFromCartStart = {
  type: typeof actionName.REMOVE_ITEM_FROM_CART_START;
  payload: RemoveItemFromCartStartPayload;
};

export type RemoveItemFromCartSucceeded = {
  type: typeof actionName.REMOVE_ITEM_FROM_CART_SUCCEEDED;
  payload: RemoveItemFromCartSucceededPayload;
};

export type RemoveItemFromCartFailed = {
  type: typeof actionName.REMOVE_ITEM_FROM_CART_FAILED;
  payload: RemoveItemFromCartFailedPayload;
};

