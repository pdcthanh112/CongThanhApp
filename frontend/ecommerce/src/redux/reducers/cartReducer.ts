import { CartState } from '@redux/actions/type/cart';
import { PayloadAction, createSlice } from '@reduxjs/toolkit';
import {
  AddItemToCartFailedPayload,
  AddItemToCartStartPayload,
  AddItemToCartSucceededPayload,
  CreateNewCartFailedPayload,
  CreateNewCartStartPayload,
  CreateNewCartSucceededPayload,
  DeleteCartFailedPayload,
  DeleteCartStartPayload,
  DeleteCartSucceededPayload,
  FetchCartFailedPayload,
  FetchCartStartPayload,
  FetchCartSucceededPayload,
  RemoveItemFromCartFailedPayload,
  RemoveItemFromCartStartPayload,
  RemoveItemFromCartSucceededPayload,
} from '@redux/actions/payload/cart';

const initialState: CartState = {
  status: 'idle',
  data: [],
  error: null,
};

const cartSlice = createSlice({
  name: 'cart',
  initialState: initialState,
  reducers: {
    fetchCartStart: (state: CartState, action: PayloadAction<FetchCartStartPayload>) => {
      state.status = 'pending';
    },
    fetchCartSucceeded: (state: CartState, action: PayloadAction<FetchCartSucceededPayload>) => {
      state.status = 'succeeded';
      state.data = action.payload.data;
    },
    fetchCartFailed: (state: CartState, action: PayloadAction<FetchCartFailedPayload>) => {
      state.status = 'failed';
      state.error = 'loi';
    },
    fetchCartClear: (state: CartState, action: PayloadAction<FetchCartFailedPayload>) => {
      state.status = 'idle';
      state.error = null;
    },
    createNewCartStart: (state: CartState, action: PayloadAction<CreateNewCartStartPayload>) => {
      state.status = 'pending';
    },
    createNewCartSucceeded: (state: CartState, action: PayloadAction<CreateNewCartSucceededPayload>) => {
      state.status = 'succeeded';
      state.data = action.payload.data;
    },
    createNewCartFailed: (state: CartState, action: PayloadAction<CreateNewCartFailedPayload>) => {
      state.status = 'failed';
      state.error = 'loi';
    },
    deleteCartStart: (state: CartState, action: PayloadAction<DeleteCartStartPayload>) => {
      state.status = 'pending';
    },
    deleteCartSucceeded: (state: CartState, action: PayloadAction<DeleteCartSucceededPayload>) => {
      state.status = 'succeeded';
      state.data = action.payload.data;
    },
    deleteCartFailed: (state: CartState, action: PayloadAction<DeleteCartFailedPayload>) => {
      state.status = 'failed';
      state.error = 'loi';
    },
    addItemToCartStart: (state: CartState, action: PayloadAction<AddItemToCartStartPayload>) => {
      state.status = 'pending';
    },
    addItemToCartSucceeded: (state: CartState, action: PayloadAction<AddItemToCartSucceededPayload>) => {
      state.status = 'succeeded';
      state.data = action.payload.data;
    },
    addItemToCartFailed: (state: CartState, action: PayloadAction<AddItemToCartFailedPayload>) => {
      state.status = 'failed';
      state.error = 'loi';
    },
    removeItemFromCartStart: (state: CartState, action: PayloadAction<RemoveItemFromCartStartPayload>) => {
      state.status = 'pending';
    },
    removeItemFromCartSucceeded: (state: CartState, action: PayloadAction<RemoveItemFromCartSucceededPayload>) => {
      state.status = 'succeeded';
      state.data = action.payload;
    },
    removeItemFromCartFailed: (state: CartState, action: PayloadAction<RemoveItemFromCartFailedPayload>) => {
      state.status = 'failed';
      state.error = 'loi';
    },
  },
});

export const {
  fetchCartStart,
  fetchCartSucceeded,
  fetchCartFailed,
  fetchCartClear,
  createNewCartStart,
  createNewCartSucceeded,
  createNewCartFailed,
  deleteCartStart,
  deleteCartSucceeded,
  deleteCartFailed,
  addItemToCartStart,
  addItemToCartSucceeded,
  addItemToCartFailed,
  removeItemFromCartStart,
  removeItemFromCartSucceeded,
  removeItemFromCartFailed,
} = cartSlice.actions;
export default cartSlice.reducer;
